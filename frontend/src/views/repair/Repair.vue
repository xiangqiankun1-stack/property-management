<template>
  <div class="repair-container">
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="报修编号">
          <el-input v-model="searchForm.repairNo" placeholder="请输入报修编号" clearable />
        </el-form-item>
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
          <el-option label="已完成" :value="2" />
          <el-option label="已撤销" :value="3" />
        </el-select>
      </el-form-item>

      <el-form-item label="报修类型">
        <el-select 
          v-model="searchForm.repairType" 
          placeholder="选择报修类型" 
          clearable
          style="width: 140px"
        >
          <el-option label="全部" value="" />
          <el-option label="水电" value="水电" />
          <el-option label="门窗" value="门窗" />
          <el-option label="设施" value="设施" />
          <el-option label="其他" value="其他" />
        </el-select>
      </el-form-item>
        <!-- <el-form-item label="业主ID">
          <el-input v-model.number="searchForm.ownerId" type="number" placeholder="请输入业主ID" clearable />
        </el-form-item> -->
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
       
        新增报修
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
          <span>已完成: <strong>{{ completedCount }}</strong> 条</span>
        </div>
      </div>
      
      <el-table :data="displayData" border stripe :loading="loading" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" align="center"/>
        <el-table-column prop="repairNo" label="报修编号" min-width="150"/>
        <el-table-column prop="ownerName" label="报修人" width="100"/>
        <el-table-column prop="houseNumber" label="房号" width="100"/>
        <el-table-column prop="repairType" label="报修类型" width="100"/>
        <el-table-column prop="repairContent" label="报修内容" min-width="200"/>
        <el-table-column prop="contactPhone" label="联系电话" width="130"/>
        <el-table-column prop="expectedTime" label="期望时间" width="180"/>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="报修时间" width="180"/>
        <el-table-column label="操作" width="165" align="center">
          <template #default="scope">
            <el-button size="small" @click="openDialog('edit', scope.row)">
             
              编辑
            </el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">
             
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="pagination.currentPage"
        v-model:page-size="pagination.pageSize"
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
        <!-- <el-form-item label="报修编号" prop="repairNo">
          <el-input v-model="form.repairNo" placeholder="请输入报修编号" />
        </el-form-item> -->
        <!-- <el-form-item label="业主ID" prop="ownerId">
          <el-input v-model.number="form.ownerId" type="number" placeholder="请输入业主ID" />
        </el-form-item> -->
        <el-form-item label="房屋ID" prop="houseId">
          <el-input v-model.number="form.houseId" type="number" placeholder="请输入房屋ID" />
        </el-form-item>
        <el-form-item label="报修类型" prop="repairType">
          <el-select v-model="form.repairType" placeholder="请选择报修类型">
            <el-option label="水电" value="水电" />
            <el-option label="门窗" value="门窗" />
            <el-option label="设施" value="设施" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="报修内容" prop="repairContent">
          <el-textarea v-model="form.repairContent" placeholder="请输入报修内容" :rows="3" />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="期望时间" prop="expectedTime">
          <el-date-picker v-model="form.expectedTime" type="datetime" placeholder="请选择期望时间" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status">
            <el-option label="待处理" :value="0" />
            <el-option label="处理中" :value="1" />
            <el-option label="已完成" :value="2" />
            <el-option label="已撤销" :value="3" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, RefreshLeft, Plus, Edit, Delete } from '@element-plus/icons-vue'
import { getRepairList, createRepair, updateRepair, deleteRepair } from '@/api/repair'

const searchForm = reactive({ 
  repairNo: '', 
  status: '',
  repairType: '',
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
  id: '', 
  repairNo: '', 
  ownerId: '', 
  houseId: '', 
  repairType: '水电', 
  repairContent: '', 
  contactPhone: '', 
  expectedTime: '',
  status: 0 
})

const rules = {
  repairNo: [{ required: true, message: '请输入报修编号', trigger: 'blur' }],
  ownerId: [{ required: true, message: '请输入业主ID', trigger: 'blur' }, { type: 'number', message: '必须是数字', trigger: 'blur' }],
  houseId: [{ required: true, message: '请输入房屋ID', trigger: 'blur' }, { type: 'number', message: '必须是数字', trigger: 'blur' }],
  repairType: [{ required: true, message: '请选择报修类型', trigger: 'blur' }],
  repairContent: [{ required: true, message: '请输入报修内容', trigger: 'blur' }],
  contactPhone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }],
  expectedTime: [{ required: true, message: '请选择期望时间', trigger: 'blur' }]
}

// 状态映射
const statusMap = {
  0: { text: '待处理', type: 'warning' },
  1: { text: '处理中', type: 'primary' },
  2: { text: '已完成', type: 'success' },
  3: { text: '已撤销', type: 'danger' }
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

const completedCount = computed(() => {
  return tableData.value.filter(item => item.status === 2).length
})

// 当前页显示的数据
const displayData = computed(() => {
  const start = (pagination.currentPage - 1) * pagination.pageSize
  const end = start + pagination.pageSize
  return tableData.value.slice(start, end)
})

const dialogTitle = computed(() => dialogType.value === 'add' ? '新增报修' : '编辑报修')

// 前端搜索过滤
const filterData = () => {
  let filtered = [...allData.value]
  
  if (searchForm.repairNo) {
    filtered = filtered.filter(item => 
      item.repairNo.toLowerCase().includes(searchForm.repairNo.toLowerCase())
    )
  }
  
  if (searchForm.status !== '') {
    filtered = filtered.filter(item => 
      item.status === searchForm.status
    )
  }
  
  if (searchForm.repairType) {
    filtered = filtered.filter(item => 
      item.repairType === searchForm.repairType
    )
  }
  
  if (searchForm.ownerId) {
    filtered = filtered.filter(item => 
      item.ownerId === searchForm.ownerId
    )
  }
  
  return filtered
}

// 更新表格数据（直接使用后端字段）
const updateTableData = () => {
  const filtered = filterData()
  tableData.value = filtered.map(item => ({
    id: item.id,
    repairNo: item.repairNo,
    ownerName: `业主${item.ownerId}`,
    houseNumber: `房屋${item.houseId}`,
    repairType: item.repairType || '未知',  // 使用后端的字符串类型
    repairContent: item.repairContent,      // 使用后端的字段名
    contactPhone: item.contactPhone || '-', // 新增联系电话
    expectedTime: item.expectedTime || '-', // 新增期望时间
    status: item.status,
    createTime: item.createTime
  }))
  pagination.total = tableData.value.length
  pagination.currentPage = 1
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getRepairList()
    
    if (Array.isArray(res) && res.length > 0) {
      allData.value = res
    } else {
      // 使用后端格式的模拟数据
      allData.value = [
        { id: 1, repairNo: 'RP20260201001', ownerId: 1, houseId: 1, repairType: '水电', repairContent: '厨房水龙头漏水，请尽快维修', repairImages: null, contactPhone: '13900000001', expectedTime: '2026-02-02T09:00:00', status: 3, createTime: '2026-02-01T08:30:00' },
        { id: 2, repairNo: 'RP20260203001', ownerId: 2, houseId: 2, repairType: '门窗', repairContent: '卧室窗户密封条老化，通风时有异响', repairImages: null, contactPhone: '13900000002', expectedTime: '2026-02-05T10:00:00', status: 1, createTime: '2026-02-03T14:00:00' }
      ]
    }
    
    updateTableData()
  } catch (error) {
    console.error('加载报修列表失败:', error)
    allData.value = [
      { id: 1, repairNo: 'RP20260201001', ownerId: 1, houseId: 1, repairType: '水电', repairContent: '厨房水龙头漏水，请尽快维修', repairImages: null, contactPhone: '13900000001', expectedTime: '2026-02-02T09:00:00', status: 3, createTime: '2026-02-01T08:30:00' },
      { id: 2, repairNo: 'RP20260203001', ownerId: 2, houseId: 2, repairType: '门窗', repairContent: '卧室窗户密封条老化，通风时有异响', repairImages: null, contactPhone: '13900000002', expectedTime: '2026-02-05T10:00:00', status: 1, createTime: '2026-02-03T14:00:00' }
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
  searchForm.repairNo = ''
  searchForm.status = ''
  searchForm.repairType = ''
  searchForm.ownerId = ''
  updateTableData() 
}

const openDialog = (type, row = null) => {
  dialogType.value = type
  dialogVisible.value = true
  if (formRef.value) formRef.value.resetFields()
  if (type === 'add') {
    Object.assign(form, { id: '', repairNo: '', ownerId: '', houseId: '', repairType: '水电', repairContent: '', contactPhone: '', expectedTime: '', status: 0 })
  } else if (type === 'edit' && row) {
    Object.assign(form, row)
  }
}

const handleDialogClose = () => { if (formRef.value) formRef.value.resetFields() }

const handleSubmit = async () => {
  if (!formRef.value) return
  const valid = await formRef.value.validate()
  if (!valid) return
  try {
    if (dialogType.value === 'add') {
      await createRepair(form)
      ElMessage.success('新增成功')
    } else {
      await updateRepair(form.id, form)
      ElMessage.success('修改成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error(dialogType.value === 'add' ? '新增失败' : '修改失败')
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该报修记录吗？', '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    await deleteRepair(id)
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
.repair-container {
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
  background: linear-gradient(90deg, #F56C6C 0%, #409EFF 50%, #67C23A 100%);
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
  color: #F56C6C;
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
  background: #F56C6C;
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
  box-shadow: 0 0 0 3px rgba(245, 108, 108, 0.1);
  border-color: #F56C6C;
}

:deep(.el-input__inner) {
  font-size: 14px;
  padding: 10px 14px;
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
  font-weight: 490;
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
</style>