<template>
  <div class="user-container">
    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="用户名">
          <el-input v-model="searchForm.userName" placeholder="请输入用户名" clearable />
        </el-form-item>
        <!-- <el-form-item label="真实姓名">
          <el-input v-model="searchForm.fullName" placeholder="请输入真实姓名" clearable />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="searchForm.phoneNumber" placeholder="请输入手机号" clearable />
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
        
        新增用户
      </el-button>
    </el-card>

    <!-- 数据表格 -->
    <el-card class="table-card">
      <div class="table-header">
        <div class="summary-info">
          <span>共 <strong>{{ pagination.total }}</strong> 条记录</span>
        </div>
      </div>
      
      <el-table 
        :data="displayData" 
        border
        stripe
        :loading="loading"
        style="width: 100%"
      >
        <el-table-column prop="id" label="ID" width="80" align="center"/>
        <el-table-column prop="userName" label="用户名"/>
        <el-table-column prop="fullName" label="真实姓名"/>
        <el-table-column prop="phoneNumber" label="手机号"/>
        <el-table-column prop="description" label="描述"/>
        <el-table-column prop="createTime" label="创建时间" width="180"/>
        <el-table-column label="操作" width="165" align="center">
          <template #default="scope">
            <el-button 
              size="small" 
              @click="openDialog('edit', scope.row)"
            >
             
              编辑
            </el-button>
            <el-button 
              size="small" 
              type="danger"
              @click="handleDelete(scope.row.id)"
            >
             
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
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

    <!-- 弹窗表单 -->
    <el-dialog 
      v-model="dialogVisible" 
      :title="dialogTitle"
      width="500px"
      @close="handleDialogClose"
    >
      <el-form 
        :model="form" 
        ref="formRef"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" :prop="isEdit ? '' : 'password'">
          <el-input 
            v-model="form.password" 
            type="password" 
            :placeholder="isEdit ? '不填则保持不变' : '请输入密码'"
          />
        </el-form-item>
        <el-form-item label="真实姓名" prop="fullName">
          <el-input v-model="form.fullName" placeholder="请输入真实姓名" />
        </el-form-item>
        <el-form-item label="手机号" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" placeholder="请输入描述" />
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
import {
  getUserList,
  createUser,
  updateUser,
  deleteUser
} from '@/api/user'

// 搜索表单
const searchForm = reactive({
  userName: '',
  fullName: '',
  phoneNumber: ''
})

// 表格数据
const allData = ref([])
const tableData = ref([])
const loading = ref(false)

// 分页
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// 弹窗
const dialogVisible = ref(false)
const dialogType = ref('add')
const formRef = ref(null)
const form = reactive({
  id: '',
  userName: '',
  password: '',
  fullName: '',
  phoneNumber: '',
  description: '',
  status: 1
})

// 表单验证规则
const rules = {
  userName: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在3-20个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在6-20个字符', trigger: 'blur' }
  ],
  fullName: [
    { required: true, message: '请输入真实姓名', trigger: 'blur' }
  ],
  phoneNumber: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ]
}

// 弹窗标题
const dialogTitle = computed(() => {
  return dialogType.value === 'add' ? '新增用户' : '编辑用户'
})

// 是否编辑状态
const isEdit = computed(() => {
  return dialogType.value === 'edit'
})

// 当前页显示的数据
const displayData = computed(() => {
  const start = (pagination.currentPage - 1) * pagination.pageSize
  const end = start + pagination.pageSize
  return tableData.value.slice(start, end)
})

// 前端搜索过滤
const filterData = () => {
  let filtered = [...allData.value]
  
  if (searchForm.userName) {
    filtered = filtered.filter(item => 
      item.userName.toLowerCase().includes(searchForm.userName.toLowerCase())
    )
  }
  
  if (searchForm.fullName) {
    filtered = filtered.filter(item => 
      item.fullName.toLowerCase().includes(searchForm.fullName.toLowerCase())
    )
  }
  
  if (searchForm.phoneNumber) {
    filtered = filtered.filter(item => 
      item.phoneNumber.includes(searchForm.phoneNumber)
    )
  }
  
  return filtered
}

// 更新表格数据
const updateTableData = () => {
  const filtered = filterData()
  tableData.value = filtered
  pagination.total = tableData.value.length
  pagination.currentPage = 1
}

// 加载数据
const loadData = async () => {
  loading.value = true
  try {
    const res = await getUserList()
    
    if (Array.isArray(res) && res.length > 0) {
      allData.value = res
    } else {
      allData.value = [
        { id: 1, userName: 'admin', fullName: '系统管理员', phoneNumber: '13800000001', description: '系统内置管理员', createTime: '2026-01-01T09:00:00' },
        { id: 2, userName: 'worker01', fullName: '张三', phoneNumber: '13800000002', description: '水电维修师傅', createTime: '2026-01-01T09:00:00' },
        { id: 3, userName: 'worker02', fullName: '李四', phoneNumber: '13800000003', description: '综合维修师傅', createTime: '2026-01-01T09:00:00' },
        { id: 4, userName: 'service01', fullName: '王五', phoneNumber: '13800000004', description: '物业客服', createTime: '2026-01-01T09:00:00' }
      ]
    }
    
    updateTableData()
  } catch (error) {
    console.error('加载用户列表失败:', error)
    allData.value = [
      { id: 1, userName: 'admin', fullName: '系统管理员', phoneNumber: '13800000001', description: '系统内置管理员', createTime: '2026-01-01T09:00:00' },
      { id: 2, userName: 'worker01', fullName: '张三', phoneNumber: '13800000002', description: '水电维修师傅', createTime: '2026-01-01T09:00:00' },
      { id: 3, userName: 'worker02', fullName: '李四', phoneNumber: '13800000003', description: '综合维修师傅', createTime: '2026-01-01T09:00:00' },
      { id: 4, userName: 'service01', fullName: '王五', phoneNumber: '13800000004', description: '物业客服', createTime: '2026-01-01T09:00:00' }
    ]
    updateTableData()
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  updateTableData()
}

// 重置
const handleReset = () => {
  searchForm.userName = ''
  searchForm.fullName = ''
  searchForm.phoneNumber = ''
  updateTableData()
}

// 打开弹窗
const openDialog = (type, row = null) => {
  dialogType.value = type
  dialogVisible.value = true
  
  if (formRef.value) {
    formRef.value.resetFields()
  }
  
  if (type === 'add') {
    Object.assign(form, { id: '', userName: '', password: '', fullName: '', phoneNumber: '', description: '', status: 1 })
  } else if (type === 'edit' && row) {
    Object.assign(form, { id: row.id, userName: row.userName, password: '', fullName: row.fullName, phoneNumber: row.phoneNumber, description: row.description, status: row.status || 1 })
  }
}

// 关闭弹窗
const handleDialogClose = () => {
  if (formRef.value) {
    formRef.value.resetFields()
  }
}

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return
  
  const valid = await formRef.value.validate()
  if (!valid) return
  
  try {
    if (dialogType.value === 'add') {
      await createUser(form)
      ElMessage.success('新增成功')
    } else {
      const data = { ...form }
      delete data.password
      if (form.password) {
        data.password = form.password
      }
      await updateUser(form.id, data)
      ElMessage.success('修改成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error(dialogType.value === 'add' ? '新增失败' : '修改失败')
  }
}

// 删除
const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm(
      '确定要删除该用户吗？',
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    await deleteUser(id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 分页大小改变
const handleSizeChange = (size) => {
  pagination.pageSize = size
  if (pagination.currentPage > Math.ceil(pagination.total / size)) {
    pagination.currentPage = 1
  }
}

// 当前页改变
const handleCurrentChange = (page) => {
  pagination.currentPage = page
}

// 初始化
onMounted(() => loadData())
</script>

<style scoped>
/* 容器样式 */
.user-container {
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
  background: linear-gradient(90deg, #409EFF 0%, #67C23A 50%, #E6A23C 100%);
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
  color: #409EFF;
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
  background: #409EFF;
  color: white;
}

/* 弹窗样式 */
:deep(.el-dialog) {
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
}

:deep(.el-dialog__header) {
  background: linear-gradient(135deg, #409EFF 0%, #3b82f6 100%);
  padding: 18px 24px;
}

:deep(.el-dialog__title) {
  color: white;
  font-weight: 600;
  font-size: 16px;
}

:deep(.el-dialog__headerbtn) {
  color: rgba(255, 255, 255, 0.8);
}

:deep(.el-dialog__headerbtn:hover) {
  color: white;
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
  box-shadow: 0 0 0 3px rgba(64, 158, 255, 0.1);
  border-color: #409EFF;
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
  font-weight: 500;
}
</style>