<template>
  <div class="building-container">
    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="楼栋名称">
          <el-input v-model="searchForm.buildingName" placeholder="请输入楼栋名称" clearable />
        </el-form-item>
        <el-form-item label="所属小区">
          <el-select v-model="searchForm.communityId" placeholder="请选择小区" clearable>
            <el-option label="全部" value="" />
            <el-option 
              v-for="item in communityList" 
              :key="item.id" 
              :label="item.communityName" 
              :value="item.id" 
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
            <el-option label="全部" value="" />
            <el-option label="正常" :value="1" />
            <el-option label="停用" :value="0" />
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
       
        新增楼栋
      </el-button>
    </el-card>

    <!-- 数据表格 -->
    <el-card class="table-card">
      <div class="table-header">
        <div class="summary-info">
          <span>共 <strong>{{ pagination.total }}</strong> 条记录</span>
          <span class="divider">|</span>
          <span>正常: <strong>{{ normalCount }}</strong> 条</span>
          <span class="divider">|</span>
          <span>停用: <strong>{{ disabledCount }}</strong> 条</span>
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
        <el-table-column prop="communityName" label="所属小区"/>
        <el-table-column prop="buildingName" label="楼栋名称"/>
        <el-table-column prop="totalFloors" label="总层数" width="100" align="center"/>
        <el-table-column prop="unitsPerFloor" label="每层户数" width="120" align="center"/>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ scope.row.status === 1 ? '正常' : '停用' }}
            </el-tag>
          </template>
        </el-table-column>
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
        <el-form-item label="所属小区" prop="communityId">
          <el-select v-model="form.communityId" placeholder="请选择小区">
            <el-option 
              v-for="item in communityList" 
              :key="item.id" 
              :label="item.communityName" 
              :value="item.id" 
            />
          </el-select>
        </el-form-item>
        <el-form-item label="楼栋名称" prop="buildingName">
          <el-input v-model="form.buildingName" placeholder="请输入楼栋名称" />
        </el-form-item>
        <el-form-item label="总层数" prop="totalFloors">
          <el-input v-model.number="form.totalFloors" type="number" placeholder="请输入总层数" />
        </el-form-item>
        <el-form-item label="每层户数" prop="unitsPerFloor">
          <el-input v-model.number="form.unitsPerFloor" type="number" placeholder="请输入每层户数" />
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" placeholder="请输入描述" />
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
  getBuildingList,
  createBuilding,
  updateBuilding,
  deleteBuilding
} from '@/api/building'
import { getCommunityList } from '@/api/community'

// 小区列表（用于下拉选择）
const communityList = ref([])

// 搜索表单
const searchForm = reactive({
  buildingName: '',
  communityId: '',
  status: ''
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
  communityId: '',
  buildingName: '',
  totalFloors: '',
  unitsPerFloor: '',
  status: 1,
  description: ''
})

// 表单验证规则
const rules = {
  communityId: [
    { required: true, message: '请选择所属小区', trigger: 'blur' }
  ],
  buildingName: [
    { required: true, message: '请输入楼栋名称', trigger: 'blur' }
  ],
  totalFloors: [
    { required: true, message: '请输入总层数', trigger: 'blur' },
    { type: 'number', min: 1, message: '总层数至少为1', trigger: 'blur' }
  ],
  unitsPerFloor: [
    { required: true, message: '请输入每层户数', trigger: 'blur' },
    { type: 'number', min: 1, message: '每层户数至少为1', trigger: 'blur' }
  ]
}

// 弹窗标题
const dialogTitle = computed(() => {
  return dialogType.value === 'add' ? '新增楼栋' : '编辑楼栋'
})

// 计算统计信息
const normalCount = computed(() => {
  return tableData.value.filter(item => item.status === 1).length
})

const disabledCount = computed(() => {
  return tableData.value.filter(item => item.status === 0).length
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
  
  if (searchForm.buildingName) {
    filtered = filtered.filter(item => 
      item.buildingName.toLowerCase().includes(searchForm.buildingName.toLowerCase())
    )
  }
  
  if (searchForm.communityId !== '') {
    filtered = filtered.filter(item => 
      item.communityId === searchForm.communityId
    )
  }
  
  if (searchForm.status !== '') {
    filtered = filtered.filter(item => 
      item.status === searchForm.status
    )
  }
  
  return filtered
}

// 更新表格数据
const updateTableData = () => {
  const filtered = filterData()
  tableData.value = filtered.map(item => {
    const community = communityList.value.find(c => c.id === item.communityId)
    return {
      ...item,
      communityName: community ? community.communityName : '未知小区'
    }
  })
  pagination.total = tableData.value.length
  pagination.currentPage = 1
}

// 加载小区列表
const loadCommunityList = async () => {
  try {
    const res = await getCommunityList()
    if (Array.isArray(res)) {
      communityList.value = res
    } else {
      communityList.value = [
        { id: 1, communityName: '阳光小区' },
        { id: 2, communityName: '幸福花园' },
        { id: 3, communityName: '和谐家园' }
      ]
    }
  } catch (error) {
    communityList.value = [
      { id: 1, communityName: '阳光小区' },
      { id: 2, communityName: '幸福花园' },
      { id: 3, communityName: '和谐家园' }
    ]
  }
}

// 加载楼栋数据
const loadData = async () => {
  loading.value = true
  try {
    const res = await getBuildingList()
    
    if (Array.isArray(res) && res.length > 0) {
      allData.value = res
    } else {
      allData.value = [
        { id: 1, communityId: 1, buildingName: '1号楼', totalFloors: 18, unitsPerFloor: 4, status: 1, createTime: '2026-01-01T09:00:00' },
        { id: 2, communityId: 1, buildingName: '2号楼', totalFloors: 18, unitsPerFloor: 4, status: 1, createTime: '2026-01-01T09:00:00' },
        { id: 3, communityId: 2, buildingName: 'A栋', totalFloors: 24, unitsPerFloor: 6, status: 1, createTime: '2026-01-02T10:00:00' },
        { id: 4, communityId: 2, buildingName: 'B栋', totalFloors: 24, unitsPerFloor: 6, status: 0, createTime: '2026-01-02T10:00:00' }
      ]
    }
    
    updateTableData()
  } catch (error) {
    console.error('加载楼栋列表失败:', error)
    allData.value = [
      { id: 1, communityId: 1, buildingName: '1号楼', totalFloors: 18, unitsPerFloor: 4, status: 1, createTime: '2026-01-01T09:00:00' },
      { id: 2, communityId: 1, buildingName: '2号楼', totalFloors: 18, unitsPerFloor: 4, status: 1, createTime: '2026-01-01T09:00:00' },
      { id: 3, communityId: 2, buildingName: 'A栋', totalFloors: 24, unitsPerFloor: 6, status: 1, createTime: '2026-01-02T10:00:00' },
      { id: 4, communityId: 2, buildingName: 'B栋', totalFloors: 24, unitsPerFloor: 6, status: 0, createTime: '2026-01-02T10:00:00' }
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
  searchForm.buildingName = ''
  searchForm.communityId = ''
  searchForm.status = ''
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
    Object.assign(form, { id: '', communityId: '', buildingName: '', totalFloors: '', unitsPerFloor: '', status: 1, description: '' })
  } else if (type === 'edit' && row) {
    Object.assign(form, { id: row.id, communityId: row.communityId, buildingName: row.buildingName, totalFloors: row.totalFloors, unitsPerFloor: row.unitsPerFloor, status: row.status, description: row.description })
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
      await createBuilding(form)
      ElMessage.success('新增成功')
    } else {
      await updateBuilding(form.id, form)
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
      '确定要删除该楼栋吗？',
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    await deleteBuilding(id)
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
onMounted(() => {
  loadCommunityList()
  loadData()
})
</script>

<style scoped>
/* 容器样式 */
.building-container {
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
  background: linear-gradient(90deg, #67C23A 0%, #409EFF 50%, #E6A23C 100%);
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
  color: #67C23A;
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
  background: #67C23A;
  color: white;
}

/* 弹窗样式 */
:deep(.el-dialog) {
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
}

:deep(.el-dialog__header) {
  background: linear-gradient(135deg, #67C23A 0%, #52c41a 100%);
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
  box-shadow: 0 0 0 3px rgba(103, 194, 58, 0.1);
  border-color: #67C23A;
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

/* 选择器样式 */
:deep(.el-select .el-input__wrapper) {
  border-radius: 8px;
}

/* 开关样式 */
:deep(.el-switch) {
  margin-top: 4px;
}
</style>