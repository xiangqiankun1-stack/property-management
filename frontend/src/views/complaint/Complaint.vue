<template>
  <div class="complaint-container">
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="业主ID">
          <el-input v-model.number="searchForm.ownerId" type="number" placeholder="请输入业主ID" clearable />
        </el-form-item>
       <!--  <el-form-item label="投诉编号">
          <el-input v-model="searchForm.complaintNo" placeholder="请输入投诉编号" clearable />
        </el-form-item> -->
        <el-form-item label="状态">
        <el-select 
          v-model="searchForm.status" 
          placeholder="请选择状态" 
          clearable
          style="width: 120px"
        >
          <el-option label="全部" value="" />
          <el-option label="待处理" :value="0" />
          <el-option label="处理中" :value="1" />
          <el-option label="已处理" :value="2" />
          <el-option label="已撤销" :value="3" />
        </el-select>
      </el-form-item>

      <el-form-item label="投诉类型">
        <el-select 
          v-model="searchForm.complaintType" 
          placeholder="选择投诉类型" 
          clearable
          style="width: 140px"
        >
          <el-option label="全部" value="" />
          <el-option label="服务态度" :value="1" />
          <el-option label="环境卫生" :value="2" />
          <el-option label="安全问题" :value="3" />
          <el-option label="设施维护" :value="4" />
          <el-option label="其他" :value="5" />
        </el-select>
      </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleSearch">
            
            搜索
          </el-button>
          <el-button @click="handleReset">
           
            重置
          </el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="openDialog('add')" class="add-btn">
        
        新增投诉
      </el-button>
    </el-card>

    <el-card class="table-card">
      <div class="table-header">
        <div class="summary-info">
          <span>共 <strong>{{ pagination.total }}</strong> 条记录</span>
          <span class="divider">|</span>
          <span>待处理: <strong>{{ pendingCount }}</strong> 条</span>
          <span class="divider">|</span>
          <span>处理中: <strong>{{ processingCount }}</strong> 条</span>
          <span class="divider">|</span>
          <span>已处理: <strong>{{ handledCount }}</strong> 条</span>
        </div>
      </div>
      
      <el-table :data="displayData" border stripe :loading="loading" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" align="center"/>
        <el-table-column prop="complaintNo" label="投诉编号" min-width="150" align="center"/>
        <el-table-column prop="ownerName" label="投诉人" width="100" align="center"/>
        <el-table-column prop="complaintType" label="投诉类型" width="100" align="center"/>
        <el-table-column prop="complaintTitle" label="投诉标题" min-width="180" align="center"/>
        <el-table-column prop="complaintContent" label="投诉内容" min-width="200" align="center"/>
        <el-table-column prop="contactPhone" label="联系电话" width="130" align="center"/>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="handleUserName" label="处理人" width="100" align="center"/>
        <el-table-column prop="handleTime" label="处理时间" width="180" align="center"/>
        <el-table-column prop="handleResult" label="处理结果" min-width="200" align="center"/>
       <!--  <el-table-column prop="satisfaction" label="满意度" width="100" align="center">
          <template #default="scope">
            <div class="satisfaction-stars">
              <span v-for="i in 5" :key="i" class="star" :class="{ active: i <= scope.row.satisfaction }">★</span>
            </div>
          </template>
        </el-table-column> -->
        <el-table-column prop="createTime" label="投诉时间" width="180"/>
        <el-table-column label="操作" width="165" align="center">
          <template #default="scope">
            <el-button size="small" @click="openHandleDialog(scope.row)">
              
              处理
            </el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">
             
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        :current-page="pagination.currentPage"
        :page-size="pagination.pageSize"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="margin-top: 20px; text-align: right"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px" @close="handleDialogClose">
      <el-form :model="form" ref="formRef" :rules="rules" label-width="100px">
       <!--  <el-form-item label="投诉编号" prop="complaintNo">
          <el-input v-model="form.complaintNo" placeholder="请输入投诉编号" />
        </el-form-item> -->
        <el-form-item label="业主ID" prop="ownerId">
          <el-input v-model.number="form.ownerId" type="number" placeholder="请输入业主ID" />
        </el-form-item>
        <el-form-item label="投诉类型" prop="complaintType">
          <el-select v-model="form.complaintType" placeholder="请选择投诉类型">
            <el-option label="服务态度" :value="1" />
            <el-option label="环境卫生" :value="2" />
            <el-option label="安全问题" :value="3" />
            <el-option label="设施维护" :value="4" />
            <el-option label="其他" :value="5" />
          </el-select>
        </el-form-item>
        <el-form-item label="投诉标题" prop="complaintTitle">
          <el-input v-model="form.complaintTitle" placeholder="请输入投诉标题" />
        </el-form-item>
        <el-form-item label="投诉内容" prop="complaintContent">
          <el-input v-model="form.complaintContent" type="textarea" placeholder="请输入投诉内容" :rows="4" />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <!-- 处理投诉弹窗 -->
    <el-dialog v-model="handleDialogVisible" title="处理投诉" width="500px" @close="handleDialogClose">
      <el-form :model="handleForm" ref="handleFormRef" :rules="handleRules" label-width="100px">
        <el-form-item label="投诉标题" disabled>
          <el-input :value="handleForm.complaintTitle" disabled />
        </el-form-item>
        <el-form-item label="投诉内容" disabled>
          <el-input :value="handleForm.complaintContent" type="textarea" disabled :rows="3" />
        </el-form-item>
        <el-form-item label="处理人ID" prop="handleUserId">
          <el-input v-model.number="handleForm.handleUserId" type="number" placeholder="请输入处理人ID" />
        </el-form-item>
        <el-form-item label="处理结果" prop="handleResult">
          <el-textarea v-model="handleForm.handleResult" placeholder="请输入处理结果" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="handleDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleComplaint">确定处理</el-button>
      </template>
    </el-dialog>

    <!-- 处理投诉弹窗 -->
    <el-dialog v-model="handleDialogVisible" title="处理投诉" width="500px" @close="handleDialogClose">
      <el-form :model="handleForm" ref="handleFormRef" :rules="handleRules" label-width="100px">
        <el-form-item label="投诉标题" disabled>
          <el-input :value="handleForm.complaintTitle" disabled />
        </el-form-item>
        <el-form-item label="投诉内容" disabled>
          <el-input :value="handleForm.complaintContent" type="textarea" disabled :rows="3" />
        </el-form-item>
        <el-form-item label="处理人ID" prop="handleUserId">
          <el-input v-model.number="handleForm.handleUserId" type="number" placeholder="请输入处理人ID" />
        </el-form-item>
        <el-form-item label="处理结果" prop="handleResult">
          <el-textarea v-model="handleForm.handleResult" placeholder="请输入处理结果" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="handleDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleComplaint">确定处理</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, RefreshLeft, Plus, Edit, Delete } from '@element-plus/icons-vue'
import { getComplaintList, createComplaint, updateComplaint, deleteComplaint } from '@/api/complaint'

const searchForm = reactive({ 
  complaintNo: '', 
  status: '',
  complaintType: '',
  ownerId: ''
})
const allData = ref([])
const tableData = ref([])
const loading = ref(false)
const pagination = reactive({ currentPage: 1, pageSize: 10, total: 0 })

const dialogVisible = ref(false)
const dialogType = ref('add')
const formRef = ref(null)
const form = reactive({ 
  id: null, 
  ownerId: null, 
  complaintType: 1,  
  complaintTitle: '', 
  complaintContent: '', 
  contactPhone: ''
})

// 处理投诉相关
const handleDialogVisible = ref(false)
const handleFormRef = ref(null)
const handleForm = reactive({
  id: null,
  complaintTitle: '',
  complaintContent: '',
  handleUserId: null,
  handleResult: ''
})

const rules = {
  ownerId: [{ required: true, message: '请输入业主ID', trigger: 'blur' }, { type: 'number', message: '必须是数字', trigger: 'blur' }],
  complaintType: [{ required: true, message: '请选择投诉类型', trigger: 'blur' }],
  complaintTitle: [{ required: true, message: '请输入投诉标题', trigger: 'blur' }],
  complaintContent: [{ required: true, message: '请输入投诉内容', trigger: 'blur' }],
  contactPhone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }]
}

// 处理投诉表单规则
const handleRules = {
  handleUserId: [{ required: true, message: '请输入处理人ID', trigger: 'blur' }, { type: 'number', message: '必须是数字', trigger: 'blur' }],
  handleResult: [{ required: true, message: '请输入处理结果', trigger: 'blur' }]
}

// 投诉类型映射
const complaintTypeMap = {
  1: '服务态度',
  2: '环境卫生',
  3: '安全问题',
  4: '设施维护',
  5: '其他'
}

// 状态映射
const statusMap = {
  0: { text: '待处理', type: 'warning' },
  1: { text: '处理中', type: 'primary' },
  2: { text: '已处理', type: 'success' },
  3: { text: '已撤销', type: 'default' }
}

const getStatusText = (status) => {
  return statusMap[status]?.text || '未知'
}

const getStatusType = (status) => {
  return statusMap[status]?.type || 'default'
}

// 计算统计信息
const pendingCount = computed(() => {
  return tableData.value.filter(item => item.status === 0).length
})

const processingCount = computed(() => {
  return tableData.value.filter(item => item.status === 1).length
})

const handledCount = computed(() => {
  return tableData.value.filter(item => item.status === 2).length
})

// 当前页显示的数据
const displayData = computed(() => {
  const start = (pagination.currentPage - 1) * pagination.pageSize
  const end = start + pagination.pageSize
  return tableData.value.slice(start, end)
})

const dialogTitle = computed(() => dialogType.value === 'add' ? '新增投诉' : '编辑/处理投诉')

// 前端搜索过滤
const filterData = () => {
  let filtered = [...allData.value]
  
  if (searchForm.complaintNo) {
    filtered = filtered.filter(item => 
      item.complaintNo.toLowerCase().includes(searchForm.complaintNo.toLowerCase())
    )
  }
  
  if (searchForm.status !== '') {
    filtered = filtered.filter(item => 
      item.status === searchForm.status
    )
  }
  
  if (searchForm.complaintType !== '') {
    filtered = filtered.filter(item => 
      item.complaintType === searchForm.complaintType
    )
  }
  
  if (searchForm.ownerId) {
    filtered = filtered.filter(item => 
      item.ownerId === searchForm.ownerId
    )
  }
  
  return filtered
}

// 更新表格数据（使用后端实际字段）
const updateTableData = () => {
  const filtered = filterData()
  tableData.value = filtered.map(item => ({
    id: item.id,
    complaintNo: item.complaintNo,
    ownerName: `业主${item.ownerId}`,
    complaintType: complaintTypeMap[item.complaintType] || '未知',  // 映射数字到中文              
    complaintTitle: item.complaintTitle || '-',                    // 新增投诉标题
    complaintContent: item.complaintContent,
    contactPhone: item.contactPhone || '-',
    status: item.status,
    handleUserName: item.handleUserId ? `员工${item.handleUserId}` : '-',  // 处理人
    handleTime: item.handleTime || '-',          // 处理时间
    handleResult: item.handleResult || '-',      // 处理结果
    satisfaction: item.satisfaction || 0,        // 满意度
    createTime: item.createTime
  }))
  pagination.total = tableData.value.length
  pagination.currentPage = 1
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getComplaintList()
    
    if (Array.isArray(res) && res.length > 0) {
      allData.value = res
    } else {
      // 使用后端格式的模拟数据
      allData.value = [
        { id: 1, complaintNo: 'CP20260205001', ownerId: 3, complaintType: 1, complaintCategory: '环境卫生', complaintTitle: '楼道垃圾清理不及时', complaintContent: '2单元楼道垃圾桶经常堆满，希望增加清运频次', complaintImages: null, contactPhone: '13900000003', handleUserId: 4, handleTime: '2026-02-06T09:00:00', handleResult: '已与保洁公司沟通，增加每日清运次数', status: 2, satisfaction: 4, createTime: '2026-02-05T11:00:00' }
      ]
    }
    
    updateTableData()
  } catch (error) {
    console.error('加载投诉列表失败:', error)
    allData.value = [
      { id: 1, complaintNo: 'CP20260205001', ownerId: 3, complaintType: 1, complaintCategory: '环境卫生', complaintTitle: '楼道垃圾清理不及时', complaintContent: '2单元楼道垃圾桶经常堆满，希望增加清运频次', complaintImages: null, contactPhone: '13900000003', handleUserId: 4, handleTime: '2026-02-06T09:00:00', handleResult: '已与保洁公司沟通，增加每日清运次数', status: 2, satisfaction: 4, createTime: '2026-02-05T11:00:00' }
    ]
    updateTableData()
  } finally {
    loading.value = false
  }
}

const handleSearch = () => { 
  updateTableData() 
}

const handleReset = () => { 
  searchForm.complaintNo = ''
  searchForm.status = ''
  searchForm.complaintType = ''
  searchForm.ownerId = ''
  updateTableData() 
}

const openDialog = (type, row = null) => {
  dialogType.value = type
  dialogVisible.value = true
  if (formRef.value) formRef.value.resetFields()
  if (type === 'add') {
    Object.assign(form, { id: '', ownerId: '', complaintType: 1, complaintCategory: '', complaintTitle: '', complaintContent: '', contactPhone: '' })
  }
}

const handleDialogClose = () => { if (formRef.value) formRef.value.resetFields() }

// 打开处理投诉弹窗
const openHandleDialog = (row) => {
  handleDialogVisible.value = true
  if (handleFormRef.value) handleFormRef.value.resetFields()
  Object.assign(handleForm, {
    id: row.id,
    complaintTitle: row.complaintTitle,
    complaintContent: row.complaintContent,
    handleResult: ''
  })
}


const handleDialogClose2 = () => { if (handleFormRef.value) handleFormRef.value.resetFields() }

const handleSubmit = async () => {
  if (!formRef.value) return
  const valid = await formRef.value.validate()
  if (!valid) return
  try {
    await createComplaint(form)
    ElMessage.success('新增成功')
    dialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error('新增失败')
  }
}

// 处理投诉
const handleComplaint = async () => {
  if (!handleFormRef.value) return
  const valid = await handleFormRef.value.validate()
  if (!valid) return
  try {
    await handleComplaintApi(handleForm.id, { handleUserId: handleForm.handleUserId, handleResult: handleForm.handleResult })
    ElMessage.success('处理成功')
    handleDialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error('处理失败')
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该投诉记录吗？', '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    await deleteComplaint(id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') ElMessage.error('删除失败')
  }
}

const handleSizeChange = (size) => { 
  pagination.pageSize = size 
  if (pagination.currentPage > Math.ceil(pagination.total / size)) {
    pagination.currentPage = 1
  }
}

const handleCurrentChange = (page) => { 
  pagination.currentPage = page 
}

onMounted(() => loadData())
</script>

<style scoped>
/* 容器样式 */
.complaint-container {
  padding: 24px;
  min-height: calc(100vh - 60px);
  background: linear-gradient(180deg, #f0f4f8 0%, #e8ecef 100%);
}

/* 搜索卡片 */
.search-card {
  margin-bottom: 24px;
  position: relative;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
  overflow: hidden;
  background: white;
}

.search-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 4px;
  background: linear-gradient(90deg, #E74C3C 0%, #409EFF 50%, #67C23A 100%);
}

/* 新增按钮 */
.add-btn {
  position: absolute;
  right: 24px;
  top: 20px;
  padding: 10px 24px;
  font-size: 14px;
  font-weight: 500;
  border-radius: 8px;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.add-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(64, 158, 255, 0.3);
}

/* 表格卡片 */
.table-card {
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
  overflow: hidden;
  background: white;
}

/* 表格头部信息 */
.table-header {
  margin-bottom: 20px;
  padding: 16px 20px;
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
  border-radius: 8px;
  border: 1px solid #e2e8f0;
}

.summary-info {
  font-size: 14px;
  color: #64748b;
  font-weight: 500;
}

.summary-info strong {
  color: #E74C3C;
  margin: 0 4px;
  font-size: 16px;
  font-weight: 600;
}

.divider {
  margin: 0 10px;
  color: #cbd5e1;
}

/* 表格样式优化 */
:deep(.el-table) {
  border-radius: 8px;
  overflow: hidden;
}

:deep(.el-table th) {
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
  color: #475569;
  font-weight: 600;
  padding: 16px 12px;
  border-bottom: 2px solid #e2e8f0;
  font-size: 14px;
}

:deep(.el-table td) {
  padding: 14px 12px;
  transition: all 0.2s ease;
  color: #374151;
  font-size: 14px;
}

:deep(.el-table tr:hover td) {
  background: #f8fafc;
}

:deep(.el-table--striped .el-table__body tr.el-table__row--striped) {
  background: #fafbfc;
}

/* 操作按钮 */
:deep(.el-table .el-button) {
  margin-right: 12px;
  border-radius: 6px;
  padding: 6px 16px;
  font-size: 12px;
  transition: all 0.2s ease;
  white-space: nowrap;
}

:deep(.el-table .el-button:hover) {
  transform: translateY(-1px);
}

:deep(.el-table .el-button:last-child) {
  margin-right: 0;
}

/* 操作列容器 */
:deep(.el-table .cell) {
  padding: 8px 0;
}

/* 分页样式 */
:deep(.el-pagination) {
  padding: 20px;
  background: #fafbfc;
  border-top: 1px solid #e2e8f0;
}

:deep(.el-pagination .el-pager li) {
  border-radius: 6px;
  margin: 0 4px;
  min-width: 32px;
  height: 32px;
  line-height: 32px;
}

:deep(.el-pagination .el-pager li.active) {
  background: #E74C3C;
  color: white;
}

/* 弹窗样式 */
:deep(.el-dialog) {
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
}

/* 弹窗标题样式优化 */
:deep(.el-dialog__header) {
  background: linear-gradient(135deg, #a2a4a5 0%, #e1d1d1 100%);
  padding: 18px 24px;
  display: flex;
  justify-content: center;  /* ✅ 标题居中 */
  align-items: center;
  position: relative;
}

:deep(.el-dialog__title) {
  color: white;
  font-weight: 700;  /* ✅ 字体加粗 */
  font-size: 18px;  /* ✅ 稍微大一点 */
  letter-spacing: 1px;  /* ✅ 字间距 */
}

/* 关闭按钮（X）位置微调 */
:deep(.el-dialog__headerbtn) {
  position: absolute;
  right: 16px;
  top: 50%;
  transform: translateY(-50%);
  color: #333 !important;
  font-size: 20px;
}

:deep(.el-dialog__headerbtn:hover) {
  color: black;
}

:deep(.el-dialog__headerbtn .el-dialog__close) {
  font-weight: 1000;  /* ✅ X 按钮加粗 */
}

:deep(.el-dialog__body) {
  padding: 24px;
}

/* 表单样式 */
:deep(.el-form-item) {
  margin-bottom: 20px;
}

:deep(.el-form-item__label) {
  font-weight: 500;
  color: #475569;
  font-size: 14px;
}

:deep(.el-input__wrapper) {
  border-radius: 8px;
  transition: all 0.2s ease;
  box-shadow: none;
  border-color: #e2e8f0;
}

:deep(.el-input__wrapper:focus-within) {
  box-shadow: 0 0 0 3px rgba(231, 76, 60, 0.1);
  border-color: #E74C3C;
}

:deep(.el-input__inner) {
  font-size: 14px;
  padding: 10px 14px;
}

/* 文本域样式 */
:deep(.el-textarea) {
  width: 100% !important;
  display: block !important;
  visibility: visible !important;
  opacity: 1 !important;
}

:deep(.el-textarea__wrapper) {
  border-radius: 8px !important;
  border: 1px solid #e2e8f0 !important;
  box-shadow: none !important;
  transition: all 0.2s ease;
  min-height: 120px !important;
  background: #ffffff !important;
  display: block !important;
  visibility: visible !important;
  opacity: 1 !important;
  position: relative !important;
  z-index: 1 !important;
}

:deep(.el-textarea__wrapper:hover) {
  border-color: #cbd5e1 !important;
}

:deep(.el-textarea__wrapper.is-focus) {
  outline: none !important;
  box-shadow: 0 0 0 3px rgba(231, 76, 60, 0.1) !important;
  border-color: #E74C3C !important;
}

:deep(.el-textarea__inner) {
  font-size: 14px !important;
  padding: 12px 14px !important;
  border-radius: 8px !important;
  border: none !important;
  transition: all 0.2s ease !important;
  min-height: 100px !important;
  resize: vertical !important;
  background: transparent !important;
  color: #374151 !important;
  display: block !important;
  visibility: visible !important;
  opacity: 1 !important;
  cursor: text !important;
}

:deep(.el-textarea__inner:focus) {
  outline: none !important;
}

:deep(.el-textarea__inner::placeholder) {
  color: #9ca3af !important;
}

/* 弹窗按钮 */
:deep(.el-dialog__footer) {
  padding: 16px 24px;
  border-top: 1px solid #e2e8f0;
}

:deep(.el-dialog__footer .el-button) {
  padding: 10px 24px;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
}

/* 选择器样式 */
:deep(.el-select .el-input__wrapper) {
  border-radius: 8px;
}

/* 日期选择器样式 */
:deep(.el-date-editor) {
  border-radius: 8px;
}

/* 文本域样式 */
:deep(.el-textarea__inner) {
  border-radius: 8px;
  font-size: 14px;
}

/* 满意度星星 */
.satisfaction-stars {
  font-size: 18px;
  letter-spacing: 2px;
}

.star {
  color: #e2e8f0;
  transition: color 0.2s ease;
}

.star.active {
  color: #f5a623;
  text-shadow: 0 1px 2px rgba(245, 166, 35, 0.3);
}
</style>