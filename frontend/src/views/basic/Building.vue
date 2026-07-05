<template>
  <div class="building-container">
    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="楼栋名称">
          <el-input v-model="searchForm.buildingName" placeholder="请输入楼栋名称" />
        </el-form-item>
        <el-form-item label="所属小区">
          <el-select v-model="searchForm.communityId" placeholder="请选择小区">
            <el-option label="全部" value="" />
            <el-option 
              v-for="item in communityList" 
              :key="item.id" 
              :label="item.communityName" 
              :value="item.id" 
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="openDialog('add')" class="add-btn">

        新增楼栋
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
        <el-table-column label="操作" width="200" align="center">
          <template #default="scope">
            <el-button 
              size="small" 
              @click="openDialog('edit', scope.row)"
            >
              <el-icon>Edit</el-icon>
              编辑
            </el-button>
            <el-button 
              size="small" 
              type="danger"
              @click="handleDelete(scope.row.id)"
            >
              <el-icon>Delete</el-icon>
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
  communityId: ''
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

// 加载小区列表
const loadCommunityList = async () => {
  try {
    const res = await getCommunityList()
    if (Array.isArray(res)) {
      communityList.value = res
    } else {
      // 模拟数据
      communityList.value = [
        { id: 1, communityName: '阳光小区' },
        { id: 2, communityName: '幸福花园' },
        { id: 3, communityName: '和谐家园' }
      ]
    }
  } catch (error) {
    console.error('加载小区列表失败:', error)
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
    const params = {
      page: pagination.currentPage,
      size: pagination.pageSize,
      ...searchForm
    }
    const res = await getBuildingList(params)
    // 后端返回的是数组，直接使用
    if (Array.isArray(res) && res.length > 0) {
      // 补充小区名称
      tableData.value = res.map(item => {
        const community = communityList.value.find(c => c.id === item.communityId)
        return {
          ...item,
          communityName: community ? community.communityName : '未知小区'
        }
      })
      pagination.total = res.length
    } else {
      // 使用模拟数据
      tableData.value = [
        { id: 1, communityId: 1, communityName: '阳光小区', buildingName: '1号楼', totalFloors: 18, unitsPerFloor: 4, status: 1, createTime: '2026-01-01T09:00:00' },
        { id: 2, communityId: 1, communityName: '阳光小区', buildingName: '2号楼', totalFloors: 18, unitsPerFloor: 4, status: 1, createTime: '2026-01-01T09:00:00' },
        { id: 3, communityId: 2, communityName: '幸福花园', buildingName: 'A栋', totalFloors: 24, unitsPerFloor: 6, status: 1, createTime: '2026-01-02T10:00:00' }
      ]
      pagination.total = tableData.value.length
    }
  } catch (error) {
    console.error('加载楼栋列表失败:', error)
    // 使用模拟数据
    tableData.value = [
      { id: 1, communityId: 1, communityName: '阳光小区', buildingName: '1号楼', totalFloors: 18, unitsPerFloor: 4, status: 1, createTime: '2026-01-01T09:00:00' },
      { id: 2, communityId: 1, communityName: '阳光小区', buildingName: '2号楼', totalFloors: 18, unitsPerFloor: 4, status: 1, createTime: '2026-01-01T09:00:00' },
      { id: 3, communityId: 2, communityName: '幸福花园', buildingName: 'A栋', totalFloors: 24, unitsPerFloor: 6, status: 1, createTime: '2026-01-02T10:00:00' }
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
  searchForm.buildingName = ''
  searchForm.communityId = ''
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
    form.communityId = ''
    form.buildingName = ''
    form.totalFloors = ''
    form.unitsPerFloor = ''
    form.status = 1
    form.description = ''
  } else if (type === 'edit' && row) {
    form.id = row.id
    form.communityId = row.communityId
    form.buildingName = row.buildingName
    form.totalFloors = row.totalFloors
    form.unitsPerFloor = row.unitsPerFloor
    form.status = row.status
    form.description = row.description
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
  loadData()
}

// 当前页改变
const handleCurrentChange = (page) => {
  pagination.currentPage = page
  loadData()
}

// 初始化
onMounted(() => {
  loadCommunityList()
  loadData()
})
</script>

<style scoped>
.building-container {
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