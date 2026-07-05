<template>
  <div class="house-container">
    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="房号">
          <el-input v-model="searchForm.houseNumber" placeholder="请输入房号" clearable />
        </el-form-item>
        <el-form-item label="所属楼栋">
          <el-select v-model="searchForm.buildingId" placeholder="请选择楼栋" clearable>
            <el-option label="全部" value="" />
            <el-option 
              v-for="item in buildingList" 
              :key="item.id" 
              :label="item.buildingName" 
              :value="item.id" 
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
            <el-option label="全部" value="" />
            <el-option label="空置" :value="0" />
            <el-option label="已入住" :value="1" />
            <el-option label="已出租" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
          <el-button @click="handleReset">
            <el-icon><RefreshLeft /></el-icon>
            重置
          </el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="openDialog('add')" class="add-btn">
        <el-icon><Plus /></el-icon>
        新增房屋
      </el-button>
    </el-card>

    <!-- 数据表格 -->
    <el-card class="table-card">
      <div class="table-header">
        <div class="summary-info">
          <span>共 <strong>{{ pagination.total }}</strong> 条记录</span>
          <span class="divider">|</span>
          <span>空置: <strong>{{ vacantCount }}</strong> 条</span>
          <span class="divider">|</span>
          <span>已入住: <strong>{{ occupiedCount }}</strong> 条</span>
          <span class="divider">|</span>
          <span>已出租: <strong>{{ rentedCount }}</strong> 条</span>
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
        <el-table-column prop="buildingName" label="所属楼栋"/>
        <el-table-column prop="houseNumber" label="房号"/>
        <el-table-column prop="floor" label="楼层" width="100" align="center"/>
        <el-table-column prop="unitNumber" label="单元号" width="100" align="center"/>
        <el-table-column prop="area" label="面积(㎡)" width="120"/>
        <el-table-column prop="houseType" label="户型"/>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180"/>
        <el-table-column label="操作" width="150" align="center">
          <template #default="scope">
            <el-button 
              size="small" 
              @click="openDialog('edit', scope.row)"
            >
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button 
              size="small" 
              type="danger"
              @click="handleDelete(scope.row.id)"
            >
              <el-icon><Delete /></el-icon>
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
      width="600px"
      @close="handleDialogClose"
    >
      <el-form 
        :model="form" 
        ref="formRef"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="所属小区">
          <el-select v-model="form.communityId" placeholder="请选择小区" disabled>
            <el-option 
              v-for="item in communityList" 
              :key="item.id" 
              :label="item.communityName" 
              :value="item.id" 
            />
          </el-select>
        </el-form-item>
        <el-form-item label="所属楼栋" prop="buildingId">
          <el-select v-model="form.buildingId" placeholder="请选择楼栋" @change="handleBuildingChange">
            <el-option 
              v-for="item in buildingList" 
              :key="item.id" 
              :label="item.buildingName" 
              :value="item.id" 
            />
          </el-select>
        </el-form-item>
        <el-form-item label="房号" prop="houseNumber">
          <el-input v-model="form.houseNumber" placeholder="请输入房号" />
        </el-form-item>
        <el-form-item label="楼层" prop="floor">
          <el-input v-model.number="form.floor" type="number" placeholder="请输入楼层" />
        </el-form-item>
        <el-form-item label="单元号">
          <el-input v-model.number="form.unitNumber" type="number" placeholder="请输入单元号" />
        </el-form-item>
        <el-form-item label="面积(㎡)">
          <el-input v-model.number="form.area" type="number" placeholder="请输入面积" />
        </el-form-item>
        <el-form-item label="户型">
          <el-input v-model="form.houseType" placeholder="请输入户型（如：一室一厅）" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="空置" :value="0" />
            <el-option label="已入住" :value="1" />
            <el-option label="已出租" :value="2" />
          </el-select>
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
  getHouseList,
  createHouse,
  updateHouse,
  deleteHouse
} from '@/api/house'
import { getCommunityList } from '@/api/community'
import { getBuildingList } from '@/api/building'

// 小区列表
const communityList = ref([])
// 楼栋列表
const buildingList = ref([])

// 搜索表单
const searchForm = reactive({
  houseNumber: '',
  buildingId: '',
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
  buildingId: '',
  houseNumber: '',
  floor: '',
  unitNumber: '',
  area: '',
  houseType: '',
  status: 0,
  description: ''
})

// 表单验证规则
const rules = {
  buildingId: [
    { required: true, message: '请选择所属楼栋', trigger: 'blur' }
  ],
  houseNumber: [
    { required: true, message: '请输入房号', trigger: 'blur' }
  ],
  floor: [
    { required: true, message: '请输入楼层', trigger: 'blur' },
    { type: 'number', min: 1, message: '楼层至少为1', trigger: 'blur' }
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'blur' }
  ]
}

// 弹窗标题
const dialogTitle = computed(() => {
  return dialogType.value === 'add' ? '新增房屋' : '编辑房屋'
})

// 获取状态类型
const getStatusType = (status) => {
  const types = {
    0: 'info',
    1: 'success',
    2: 'warning'
  }
  return types[status] || 'info'
}

// 获取状态文本
const getStatusText = (status) => {
  const texts = {
    0: '空置',
    1: '已入住',
    2: '已出租'
  }
  return texts[status] || '未知'
}

// 计算统计信息
const vacantCount = computed(() => {
  return tableData.value.filter(item => item.status === 0).length
})

const occupiedCount = computed(() => {
  return tableData.value.filter(item => item.status === 1).length
})

const rentedCount = computed(() => {
  return tableData.value.filter(item => item.status === 2).length
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
  
  if (searchForm.houseNumber) {
    filtered = filtered.filter(item => 
      item.houseNumber.toLowerCase().includes(searchForm.houseNumber.toLowerCase())
    )
  }
  
  if (searchForm.buildingId !== '') {
    filtered = filtered.filter(item => 
      item.buildingId === searchForm.buildingId
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
    const building = buildingList.value.find(b => b.id === item.buildingId)
    const community = building ? communityList.value.find(c => c.id === building.communityId) : null
    return {
      ...item,
      buildingName: building ? building.buildingName : '未知楼栋',
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

// 加载楼栋列表
const loadBuildingList = async () => {
  try {
    const res = await getBuildingList()
    if (Array.isArray(res)) {
      buildingList.value = res
    } else {
      buildingList.value = [
        { id: 1, communityId: 1, buildingName: '1号楼' },
        { id: 2, communityId: 1, buildingName: '2号楼' },
        { id: 3, communityId: 2, buildingName: 'A栋' }
      ]
    }
  } catch (error) {
    buildingList.value = [
      { id: 1, communityId: 1, buildingName: '1号楼' },
      { id: 2, communityId: 1, buildingName: '2号楼' },
      { id: 3, communityId: 2, buildingName: 'A栋' }
    ]
  }
}

// 楼栋改变时更新小区
const handleBuildingChange = (buildingId) => {
  const building = buildingList.value.find(b => b.id === buildingId)
  if (building) {
    form.communityId = building.communityId
  }
}

// 加载房屋数据
const loadData = async () => {
  loading.value = true
  try {
    const res = await getHouseList()
    
    if (Array.isArray(res) && res.length > 0) {
      allData.value = res
    } else {
      allData.value = [
        { id: 1, communityId: 1, buildingId: 1, houseNumber: '101', floor: 1, unitNumber: 1, area: 89, houseType: '两室一厅', status: 1, createTime: '2026-01-01T09:00:00' },
        { id: 2, communityId: 1, buildingId: 1, houseNumber: '102', floor: 1, unitNumber: 1, area: 105, houseType: '三室一厅', status: 1, createTime: '2026-01-01T09:00:00' },
        { id: 3, communityId: 1, buildingId: 1, houseNumber: '201', floor: 2, unitNumber: 1, area: 89, houseType: '两室一厅', status: 0, createTime: '2026-01-01T09:00:00' },
        { id: 4, communityId: 2, buildingId: 3, houseNumber: '301', floor: 3, unitNumber: 1, area: 120, houseType: '三室两厅', status: 2, createTime: '2026-01-02T10:00:00' }
      ]
    }
    
    updateTableData()
  } catch (error) {
    console.error('加载房屋列表失败:', error)
    allData.value = [
      { id: 1, communityId: 1, buildingId: 1, houseNumber: '101', floor: 1, unitNumber: 1, area: 89, houseType: '两室一厅', status: 1, createTime: '2026-01-01T09:00:00' },
      { id: 2, communityId: 1, buildingId: 1, houseNumber: '102', floor: 1, unitNumber: 1, area: 105, houseType: '三室一厅', status: 1, createTime: '2026-01-01T09:00:00' },
      { id: 3, communityId: 1, buildingId: 1, houseNumber: '201', floor: 2, unitNumber: 1, area: 89, houseType: '两室一厅', status: 0, createTime: '2026-01-01T09:00:00' },
      { id: 4, communityId: 2, buildingId: 3, houseNumber: '301', floor: 3, unitNumber: 1, area: 120, houseType: '三室两厅', status: 2, createTime: '2026-01-02T10:00:00' }
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
  searchForm.houseNumber = ''
  searchForm.buildingId = ''
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
    Object.assign(form, { id: '', communityId: '', buildingId: '', houseNumber: '', floor: '', unitNumber: '', area: '', houseType: '', status: 0, description: '' })
  } else if (type === 'edit' && row) {
    Object.assign(form, { id: row.id, communityId: row.communityId, buildingId: row.buildingId, houseNumber: row.houseNumber, floor: row.floor, unitNumber: row.unitNumber, area: row.area, houseType: row.houseType, status: row.status, description: row.description })
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
      await createHouse(form)
      ElMessage.success('新增成功')
    } else {
      await updateHouse(form.id, form)
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
      '确定要删除该房屋吗？',
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    await deleteHouse(id)
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
  loadBuildingList()
  loadData()
})
</script>

<style scoped>
.house-container {
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

.divider {
  margin: 0 10px;
  color: #ddd;
}
</style>