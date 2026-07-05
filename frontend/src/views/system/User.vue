<template>
  <div class="user-container">
    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="用户名">
          <el-input v-model="searchForm.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="openDialog('add')" class="add-btn">
        新增用户
        
      </el-button>
    </el-card>

    <!-- 数据表格 -->
    <el-card class="table-card">
      <el-table 
        :data="tableData" 
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
        <!-- <el-table-column label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag type="success">启用</el-tag>
          </template>
        </el-table-column> -->
        <el-table-column prop="createTime" label="创建时间" width="180"/>
        <el-table-column label="操作" width="200" align="center">
          <template #default="scope">
            <el-button 
              size="small" 
              @click="openDialog('edit', scope.row)"
            >
              <el-icon>编辑</el-icon>
              
            </el-button>
            <el-button 
              size="small" 
              type="danger"
              @click="handleDelete(scope.row.id)"
            >
             <el-icon>删除</el-icon>
              
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
import { ref, reactive, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getUserList,
  createUser,
  updateUser,
  deleteUser
} from '@/api/user'

// 搜索表单
const searchForm = reactive({
  username: ''
})

// 表格数据
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

// 加载数据
const loadData = async () => {
  loading.value = true
  try {
    console.log('开始加载用户列表...')
    const params = {
      page: pagination.currentPage,
      size: pagination.pageSize,
      ...searchForm
    }
    console.log('请求参数:', params)
    const res = await getUserList(params)
    console.log('后端返回数据:', res)
    
    // 后端返回的是数组，直接使用
    if (Array.isArray(res) && res.length > 0) {
      tableData.value = res
      pagination.total = res.length
      console.log('数据加载成功，共', res.length, '条记录')
    } else {
      console.warn('后端返回空数据，使用模拟数据')
      tableData.value = [
        { id: 1, userName: 'admin', fullName: '系统管理员', phoneNumber: '13800000001', description: '系统内置管理员', createTime: '2026-01-01T09:00:00' },
        { id: 2, userName: 'worker01', fullName: '张三', phoneNumber: '13800000002', description: '水电维修师傅', createTime: '2026-01-01T09:00:00' },
        { id: 3, userName: 'worker02', fullName: '李四', phoneNumber: '13800000003', description: '综合维修师傅', createTime: '2026-01-01T09:00:00' }
      ]
      pagination.total = tableData.value.length
    }
  } catch (error) {
    console.error('加载用户列表失败:', error)
    console.error('错误详情:', error.message, error.response?.data)
    // 使用模拟数据
    tableData.value = [
      { id: 1, userName: 'admin', fullName: '系统管理员', phoneNumber: '13800000001', description: '系统内置管理员', createTime: '2026-01-01T09:00:00' },
      { id: 2, userName: 'worker01', fullName: '张三', phoneNumber: '13800000002', description: '水电维修师傅', createTime: '2026-01-01T09:00:00' },
      { id: 3, userName: 'worker02', fullName: '李四', phoneNumber: '13800000003', description: '综合维修师傅', createTime: '2026-01-01T09:00:00' }
    ]
    pagination.total = tableData.value.length
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  pagination.currentPage = 1
  loadData()
}

// 重置
const handleReset = () => {
  searchForm.username = ''
  handleSearch()
}

// 打开弹窗
const openDialog = (type, row = null) => {
  dialogType.value = type
  dialogVisible.value = true
  
  // 重置表单
  if (formRef.value) {
    formRef.value.resetFields()
  }
  
  if (type === 'add') {
    form.id = ''
    form.userName = ''
    form.password = ''
    form.fullName = ''
    form.phoneNumber = ''
    form.description = ''
    form.status = 1
  } else if (type === 'edit' && row) {
    form.id = row.id
    form.userName = row.userName
    form.password = ''
    form.fullName = row.fullName
    form.phoneNumber = row.phoneNumber
    form.description = row.description
    form.status = row.status || 1
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
  loadData()
}

// 当前页改变
const handleCurrentChange = (page) => {
  pagination.currentPage = page
  loadData()
}

// 初始化
loadData()
</script>

<style scoped>
.user-container {
  padding: 20px;
}

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

.table-card {
  min-height: 400px;
}
</style>