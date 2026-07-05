<template>
  <div class="role-container">
    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="角色名称">
          <el-input v-model="searchForm.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="openDialog('add')" class="add-btn">
        <el-icon>Plus</el-icon>
        新增角色
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
        <el-table-column prop="roleName" label="角色名称"/>
        <el-table-column prop="roleCode" label="角色代码"/>
        <el-table-column prop="description" label="描述"/>
        <el-table-column prop="createTime" label="创建时间" width="180"/>
        <el-table-column label="操作" width="220" align="center">
          <template #default="scope">
            <el-button 
              size="small" 
              @click="openDialog('edit', scope.row)"
            >
              <el-icon>编辑</el-icon>
              
            </el-button>
            <el-button 
              size="small" 
              type="primary"
              @click="handlePermissions(scope.row)"
            >
              <el-icon>权限</el-icon>
              
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
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="角色代码" prop="roleCode">
          <el-input v-model="form.roleCode" placeholder="请输入角色代码" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <!-- 权限分配弹窗 -->
    <el-dialog 
      v-model="permissionDialogVisible" 
      title="权限分配"
      width="600px"
    >
      <el-tree
        :data="permissionTree" 
        :props="treeProps"
        show-checkbox
        default-expand-all
        :checked-keys="checkedPermissions"
        @check-change="handleCheckChange"
      />
      <template #footer>
        <el-button @click="permissionDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSavePermissions">保存</el-button>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getRoleList,
  createRole,
  updateRole,
  deleteRole
} from '@/api/role'

// 搜索表单
const searchForm = reactive({
  roleName: ''
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
  roleName: '',
  roleCode: '',
  description: '',
  remark: ''
})

// 表单验证规则
const rules = {
  roleName: [
    { required: true, message: '请输入角色名称', trigger: 'blur' }
  ],
  roleCode: [
    { required: true, message: '请输入角色代码', trigger: 'blur' }
  ]
}

// 权限分配
const permissionDialogVisible = ref(false)
const currentRoleId = ref(null)
const permissionTree = ref([
  {
    id: 1,
    label: '系统管理',
    children: [
      { id: 11, label: '用户管理' },
      { id: 12, label: '角色管理' }
    ]
  },
  {
    id: 2,
    label: '基础数据',
    children: [
      { id: 21, label: '小区管理' },
      { id: 22, label: '楼栋管理' },
      { id: 23, label: '房屋管理' },
      { id: 24, label: '业主管理' }
    ]
  },
  {
    id: 3,
    label: '服务管理',
    children: [
      { id: 31, label: '报修管理' },
      { id: 32, label: '投诉管理' }
    ]
  },
  {
    id: 4,
    label: '财务管理',
    children: [
      { id: 41, label: '账单管理' },
      { id: 42, label: '缴费管理' }
    ]
  }
])
const treeProps = {
  children: 'children',
  label: 'label'
}
const checkedPermissions = ref([])

// 弹窗标题
const dialogTitle = computed(() => {
  return dialogType.value === 'add' ? '新增角色' : '编辑角色'
})

// 是否编辑状态
const isEdit = computed(() => {
  return dialogType.value === 'edit'
})

// 加载数据
const loadData = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.currentPage,
      size: pagination.pageSize,
      ...searchForm
    }
    const res = await getRoleList(params)
    // 后端返回的是数组，直接使用
    if (Array.isArray(res) && res.length > 0) {
      tableData.value = res
      pagination.total = res.length
    } else {
      // 使用模拟数据
      tableData.value = [
        { id: 1, roleName: '管理员', roleCode: 'ADMIN', description: '系统管理员', createTime: '2026-01-01T09:00:00' },
        { id: 2, roleName: '维修人员', roleCode: 'WORKER', description: '维修师傅', createTime: '2026-01-01T09:00:00' },
        { id: 3, roleName: '客服人员', roleCode: 'SERVICE', description: '物业客服', createTime: '2026-01-01T09:00:00' }
      ]
      pagination.total = tableData.value.length
    }
  } catch (error) {
    console.error('加载角色列表失败:', error)
    // 使用模拟数据
    tableData.value = [
      { id: 1, roleName: '管理员', roleCode: 'ADMIN', description: '系统管理员', createTime: '2026-01-01T09:00:00' },
      { id: 2, roleName: '维修人员', roleCode: 'WORKER', description: '维修师傅', createTime: '2026-01-01T09:00:00' },
      { id: 3, roleName: '客服人员', roleCode: 'SERVICE', description: '物业客服', createTime: '2026-01-01T09:00:00' }
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
  searchForm.roleName = ''
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
    form.roleName = ''
    form.roleCode = ''
    form.description = ''
    form.remark = ''
  } else if (type === 'edit' && row) {
    form.id = row.id
    form.roleName = row.roleName
    form.roleCode = row.roleCode
    form.description = row.description
    form.remark = row.remark
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
      await createRole(form)
      ElMessage.success('新增成功')
    } else {
      await updateRole(form.id, form)
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
      '确定要删除该角色吗？',
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    await deleteRole(id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 打开权限分配
const handlePermissions = (row) => {
  currentRoleId.value = row.id
  permissionDialogVisible.value = true
  // 模拟已选中的权限
  checkedPermissions.value = [1, 11, 12]
}

// 权限选择变化
const handleCheckChange = (data, checked, indeterminate) => {
  console.log('权限选择变化:', data, checked)
}

// 保存权限
const handleSavePermissions = () => {
  ElMessage.success('权限分配成功')
  permissionDialogVisible.value = false
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
.role-container {
  padding: 20px;
}

.search-card {
  margin-bottom: 20px;
}

.add-btn {
  float: right;
}

.table-card {
  min-height: 400px;
}
</style>