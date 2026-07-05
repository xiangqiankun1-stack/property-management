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
          <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
            <el-option label="全部" value="" />
            <el-option label="待处理" :value="0" />
            <el-option label="处理中" :value="1" />
            <el-option label="已处理" :value="2" />
            <el-option label="已撤销" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="投诉类型">
          <el-select v-model="searchForm.complaintType" placeholder="请选择投诉类型" clearable>
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
        <el-table-column prop="complaintNo" label="投诉编号" min-width="150"/>
        <el-table-column prop="ownerName" label="投诉人" width="100"/>
        <el-table-column prop="complaintType" label="投诉类型" width="100"/>
        <el-table-column prop="complaintCategory" label="投诉分类" width="120"/>
        <el-table-column prop="complaintTitle" label="投诉标题" min-width="180"/>
        <el-table-column prop="complaintContent" label="投诉内容" min-width="200"/>
        <el-table-column prop="contactPhone" label="联系电话" width="130"/>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="handleUserName" label="处理人" width="100"/>
        <el-table-column prop="handleTime" label="处理时间" width="180"/>
        <el-table-column prop="handleResult" label="处理结果" min-width="200"/>
        <el-table-column prop="satisfaction" label="满意度" width="100" align="center">
          <template #default="scope">
            <div class="satisfaction-stars">
              <span v-for="i in 5" :key="i" class="star" :class="{ active: i <= scope.row.satisfaction }">★</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="投诉时间" width="180"/>
        <el-table-column label="操作" width="200" align="center">
          <template #default="scope">
            <el-button size="small" @click="openDialog('edit', scope.row)">
              
              编辑/处理
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
        <el-form-item label="投诉编号" prop="complaintNo">
          <el-input v-model="form.complaintNo" placeholder="请输入投诉编号" />
        </el-form-item>
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
        <el-form-item label="投诉分类" prop="complaintCategory">
          <el-input v-model="form.complaintCategory" placeholder="请输入投诉分类" />
        </el-form-item>
        <el-form-item label="投诉标题" prop="complaintTitle">
          <el-input v-model="form.complaintTitle" placeholder="请输入投诉标题" />
        </el-form-item>
        <el-form-item label="投诉内容" prop="complaintContent">
          <el-textarea v-model="form.complaintContent" placeholder="请输入投诉内容" :rows="3" />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="处理人ID" prop="handleUserId">
          <el-input v-model.number="form.handleUserId" type="number" placeholder="请输入处理人ID" />
        </el-form-item>
        <el-form-item label="处理时间">
          <el-date-picker v-model="form.handleTime" type="datetime" placeholder="请选择处理时间" />
        </el-form-item>
        <el-form-item label="处理结果">
          <el-textarea v-model="form.handleResult" placeholder="请输入处理结果" :rows="3" />
        </el-form-item>
        <el-form-item label="满意度">
          <el-select v-model="form.satisfaction">
            <el-option label="1星" :value="1" />
            <el-option label="2星" :value="2" />
            <el-option label="3星" :value="3" />
            <el-option label="4星" :value="4" />
            <el-option label="5星" :value="5" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status">
            <el-option label="待处理" :value="0" />
            <el-option label="处理中" :value="1" />
            <el-option label="已处理" :value="2" />
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
  id: '', 
  complaintNo: '', 
  ownerId: '', 
  complaintType: 1, 
  complaintCategory: '', 
  complaintTitle: '', 
  complaintContent: '', 
  contactPhone: '', 
  handleUserId: '', 
  handleTime: '', 
  handleResult: '',
  satisfaction: 0,
  status: 0 
})

const rules = {
  complaintNo: [{ required: true, message: '请输入投诉编号', trigger: 'blur' }],
  ownerId: [{ required: true, message: '请输入业主ID', trigger: 'blur' }, { type: 'number', message: '必须是数字', trigger: 'blur' }],
  complaintType: [{ required: true, message: '请选择投诉类型', trigger: 'blur' }],
  complaintCategory: [{ required: true, message: '请输入投诉分类', trigger: 'blur' }],
  complaintTitle: [{ required: true, message: '请输入投诉标题', trigger: 'blur' }],
  complaintContent: [{ required: true, message: '请输入投诉内容', trigger: 'blur' }],
  contactPhone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }]
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
    complaintCategory: item.complaintCategory || '-',              // 新增投诉分类
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
    Object.assign(form, { id: '', complaintNo: '', ownerId: '', complaintType: 1, complaintCategory: '', complaintTitle: '', complaintContent: '', contactPhone: '', handleUserId: '', handleTime: '', handleResult: '', satisfaction: 0, status: 0 })
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
      await createComplaint(form)
      ElMessage.success('新增成功')
    } else {
      await updateComplaint(form.id, form)
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
.complaint-container { padding: 20px; }
.search-card { 
  margin-bottom: 20px; 
  position: relative;
}

.add-btn {
  position: absolute;
  right: 20px;
  top: 15px;
  padding: 8px 16px;
  font-size: 14px;
}

.table-card { min-height: 400px; }
.table-header { margin-bottom: 15px; padding: 10px 15px; background: #fafafa; border-radius: 4px; }
.summary-info { font-size: 14px; color: #666; }
.summary-info strong { color: #409EFF; margin: 0 2px; }
.divider { margin: 0 10px; color: #ddd; }

.satisfaction-stars {
  font-size: 16px;
}

.star {
  color: #ddd;
}

.star.active {
  color: #f5a623;
}
</style>