<template>
  <div class="role-container">
    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="角色名称">
          <el-input v-model="searchForm.roleName" placeholder="请输入角色名称" clearable />
        </el-form-item>
        <el-form-item label="角色代码">
          <el-input v-model="searchForm.roleCode" placeholder="请输入角色代码" clearable />
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
        
        新增角色
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
        <el-table-column prop="roleName" label="角色名称"/>
        <el-table-column prop="roleCode" label="角色代码"/>
        <el-table-column prop="description" label="描述"/>
        <el-table-column prop="createTime" label="创建时间" width="180"/>
        <el-table-column label="操作" width="280" align="center">
          <template #default="scope">
            <el-button size="small" @click="openDialog('edit', scope.row)">
             
              编辑
            </el-button>
            <el-button size="small" type="primary" @click="handlePermissions(scope.row)">
            
              权限
            </el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">
              
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

    <!-- 权限分配弹窗 - 平行列表 -->
    <el-dialog 
      v-model="permissionDialogVisible" 
      title="权限分配"
      width="700px"
      @close="handlePermissionDialogClose"
    >
      <div class="permission-tip">
        <span>当前角色：<strong>{{ currentRoleName }}</strong></span>
        <span style="margin-left: 20px; color: #999; font-size: 12px;">
          （勾选表示拥有该权限，取消勾选表示删除该权限）
        </span>
      </div>
      
      <el-checkbox-group v-model="checkedPermissionIds" class="permission-list">
        <el-checkbox
          v-for="item in flatPermissionList"
          :key="item.id"
          :label="item.id"
          class="permission-item"
        >
          {{ item.permissionName }}
        </el-checkbox>
      </el-checkbox-group>
      
      <template #footer>
        <el-button @click="permissionDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSavePermissions" :loading="savingPermission">
          保存权限
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, RefreshLeft, Plus, Edit, Delete, Key } from '@element-plus/icons-vue'
import {
  getRoleList,
  createRole,
  updateRole,
  deleteRole
} from '@/api/role'
import {
  getRolePermissions,
  getAllPermissions,
  assignPermissions
} from '@/api/rolePermission'

// ===== 搜索表单 =====
const searchForm = reactive({
  roleName: '',
  roleCode: ''
})

// ===== 表格数据 =====
const allData = ref([])
const tableData = ref([])
const loading = ref(false)

// ===== 分页 =====
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// ===== 弹窗 =====
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

// ===== 表单验证规则 =====
const rules = {
  roleName: [
    { required: true, message: '请输入角色名称', trigger: 'blur' }
  ],
  roleCode: [
    { required: true, message: '请输入角色代码', trigger: 'blur' }
  ]
}

// ===== 权限分配 - 平行列表 =====
const permissionDialogVisible = ref(false)
const currentRoleId = ref(null)
const currentRoleName = ref('')
const savingPermission = ref(false)

// 扁平权限列表（所有权限平铺）
const flatPermissionList = ref([])

// 当前角色选中的权限ID列表
const checkedPermissionIds = ref([])

// ===== 计算属性 =====
const dialogTitle = computed(() => {
  return dialogType.value === 'add' ? '新增角色' : '编辑角色'
})

// 当前页显示的数据
const displayData = computed(() => {
  const start = (pagination.currentPage - 1) * pagination.pageSize
  const end = start + pagination.pageSize
  return tableData.value.slice(start, end)
})

// ===== 前端搜索过滤 =====
const filterData = () => {
  let filtered = [...allData.value]
  
  if (searchForm.roleName) {
    filtered = filtered.filter(item => 
      item.roleName && item.roleName.toLowerCase().includes(searchForm.roleName.toLowerCase())
    )
  }
  
  if (searchForm.roleCode) {
    filtered = filtered.filter(item => 
      item.roleCode && item.roleCode.toLowerCase().includes(searchForm.roleCode.toLowerCase())
    )
  }
  
  return filtered
}

// ===== 更新表格数据 =====
const updateTableData = () => {
  const filtered = filterData()
  tableData.value = filtered
  pagination.total = filtered.length
  pagination.currentPage = 1
}

// ===== 加载角色数据 =====
const loadData = async () => {
  loading.value = true
  try {
    console.log('🔵 开始加载角色数据...')
    const res = await getRoleList()
    console.log('🟢 角色数据:', res)
    
    if (Array.isArray(res) && res.length > 0) {
      allData.value = res
    } else {
      // 模拟数据
      allData.value = [
        { id: 1, roleName: '管理员', roleCode: 'ADMIN', description: '系统管理员', createTime: '2026-01-01T09:00:00' },
        { id: 2, roleName: '维修人员', roleCode: 'WORKER', description: '维修师傅', createTime: '2026-01-01T09:00:00' },
        { id: 3, roleName: '客服人员', roleCode: 'SERVICE', description: '物业客服', createTime: '2026-01-01T09:00:00' },
        { id: 4, roleName: '财务人员', roleCode: 'FINANCE', description: '财务人员', createTime: '2026-01-01T09:00:00' }
      ]
    }
    
    updateTableData()
  } catch (error) {
    console.error('🔴 加载角色列表失败:', error)
    ElMessage.error('加载角色列表失败')
  } finally {
    loading.value = false
  }
}

// ===== 加载扁平权限列表 =====
const loadFlatPermissions = async () => {
  console.log('🔵 开始加载权限列表...')
  
  try {
    const res = await getAllPermissions()
    console.log('🟢 权限原始数据:', res)
    
    if (Array.isArray(res) && res.length > 0) {
      // 过滤掉父节点（目录），只显示叶子节点
      flatPermissionList.value = res.filter(item => {
        // 根据你的数据结构调整过滤条件
        // parentId 不为空表示是子节点（叶子节点）
        return item.parentId !== null && item.parentId !== undefined && item.parentId !== 0
      })
      
      // 如果过滤后没有数据，显示所有权限
      if (flatPermissionList.value.length === 0) {
        flatPermissionList.value = res
      }
      
      console.log('🟢 扁平权限列表:', flatPermissionList.value)
    } else {
      // 模拟数据
      flatPermissionList.value = [
        { id: 1, permissionName: '用户管理' },
        { id: 2, permissionName: '角色管理' },
        { id: 3, permissionName: '新增用户' },
        { id: 4, permissionName: '小区管理' },
        { id: 5, permissionName: '楼栋管理' },
        { id: 6, permissionName: '房屋管理' },
        { id: 7, permissionName: '业主管理' },
        { id: 8, permissionName: '报修管理' },
        { id: 9, permissionName: '投诉管理' },
        { id: 10, permissionName: '账单管理' },
        { id: 11, permissionName: '缴费管理' }
      ]
    }
  } catch (error) {
    console.error('🔴 加载权限列表失败:', error)
    flatPermissionList.value = [
      { id: 1, permissionName: '用户管理' },
      { id: 2, permissionName: '角色管理' },
      { id: 3, permissionName: '新增用户' },
      { id: 4, permissionName: '小区管理' },
      { id: 5, permissionName: '楼栋管理' },
      { id: 6, permissionName: '房屋管理' },
      { id: 7, permissionName: '业主管理' },
      { id: 8, permissionName: '报修管理' },
      { id: 9, permissionName: '投诉管理' },
      { id: 10, permissionName: '账单管理' },
      { id: 11, permissionName: '缴费管理' }
    ]
  }
}

// ===== 加载角色的权限（平行列表）- 去重版 =====
const loadRolePermissions = async (roleId) => {
  console.log('🔵 加载角色权限, roleId:', roleId)
  
  try {
    const res = await getRolePermissions(roleId)
    console.log('🟢 角色权限数据:', res)
    
    if (Array.isArray(res)) {
      // ✅ 去重：使用 Set 去除重复的权限ID
      const uniqueIds = [...new Set(res.map(item => item.id))]
      checkedPermissionIds.value = uniqueIds
      console.log('🟢 选中的权限ID（去重后）:', checkedPermissionIds.value)
    } else {
      checkedPermissionIds.value = []
    }
  } catch (error) {
    console.error('🔴 加载角色权限失败:', error)
    checkedPermissionIds.value = []
  }
}

// ===== 搜索 =====
const handleSearch = () => {
  updateTableData()
}

// ===== 重置 =====
const handleReset = () => {
  searchForm.roleName = ''
  searchForm.roleCode = ''
  updateTableData()
}

// ===== 打开弹窗 =====
const openDialog = (type, row = null) => {
  dialogType.value = type
  dialogVisible.value = true
  
  if (formRef.value) {
    formRef.value.resetFields()
  }
  
  if (type === 'add') {
    Object.assign(form, { id: '', roleName: '', roleCode: '', description: '', remark: '' })
  } else if (type === 'edit' && row) {
    Object.assign(form, { 
      id: row.id, 
      roleName: row.roleName, 
      roleCode: row.roleCode, 
      description: row.description, 
      remark: row.remark 
    })
  }
}

// ===== 关闭弹窗 =====
const handleDialogClose = () => {
  if (formRef.value) {
    formRef.value.resetFields()
  }
}

// ===== 提交表单 =====
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

// ===== 删除角色 =====
const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm(
      '确定要删除该角色吗？删除后该角色的所有权限关联也将被清理。',
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

// ===== 打开权限分配 =====
const handlePermissions = async (row) => {
  console.log('🔵 打开权限分配, 角色:', row.roleName, 'ID:', row.id)
  
  currentRoleId.value = row.id
  currentRoleName.value = row.roleName
  permissionDialogVisible.value = true
  
  // 先加载所有权限（如果还没加载）
  if (flatPermissionList.value.length === 0) {
    await loadFlatPermissions()
  }
  
  // 加载角色已有的权限
  await loadRolePermissions(row.id)
}

// ===== 关闭权限弹窗 =====
const handlePermissionDialogClose = () => {
  checkedPermissionIds.value = []
  currentRoleId.value = null
  currentRoleName.value = ''
}

// ===== 保存权限（平行列表）- 去重版 =====
const handleSavePermissions = async () => {
  if (!currentRoleId.value) return
  
  savingPermission.value = true
  
  try {
    // ✅ 去重：使用 Set 去除重复的权限ID
    const uniqueIds = [...new Set(checkedPermissionIds.value)]
    
    console.log('🔵 保存权限, 角色ID:', currentRoleId.value)
    console.log('🟢 原始选中的权限ID:', checkedPermissionIds.value)
    console.log('🟢 去重后的权限ID:', uniqueIds)
    
    // 调用分配权限接口
    await assignPermissions(currentRoleId.value, uniqueIds)
    
    ElMessage.success('权限分配成功')
    permissionDialogVisible.value = false
  } catch (error) {
    console.error('🔴 权限分配失败:', error)
    ElMessage.error(error.message || '权限分配失败')
  } finally {
    savingPermission.value = false
  }
}

// ===== 分页 =====
const handleSizeChange = (size) => {
  pagination.pageSize = size
  if (pagination.currentPage > Math.ceil(pagination.total / size)) {
    pagination.currentPage = 1
  }
}

const handleCurrentChange = (page) => {
  pagination.currentPage = page
}

// ===== 初始化 =====
onMounted(() => {
  console.log('🔵 组件挂载完成，开始初始化...')
  loadData()
  loadFlatPermissions()
  console.log('🔵 初始化完成')
})
</script>

<style scoped>
.role-container {
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

.table-header {
  margin-bottom: 15px;
  padding: 10px 15px;
  background: #fafafa;
  border-radius: 4px;
}

.summary-info {
  font-size: 14px;
  color: #666;
}

.summary-info strong {
  color: #409EFF;
  margin: 0 2px;
}

.permission-tip {
  margin-bottom: 15px;
  padding: 10px 15px;
  background: #f5f7fa;
  border-radius: 4px;
}

/* 权限列表样式 */
.permission-list {
  display: flex;
  flex-wrap: wrap;
  gap: 12px 20px;
  padding: 10px 0;
  max-height: 400px;
  overflow-y: auto;
}

.permission-item {
  width: 140px;
  margin: 0;
  padding: 8px 14px;
  background: #f5f7fa;
  border-radius: 6px;
  transition: all 0.2s;
}

.permission-item:hover {
  background: #e8ecf1;
}

.permission-item .el-checkbox__label {
  font-size: 14px;
  font-weight: normal;
}

.permission-item .el-checkbox__input {
  margin-right: 6px;
}
</style>