<template>
  <div class="community-container">
    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="小区名称">
          <el-input v-model="searchForm.communityName" placeholder="请输入小区名称" clearable />
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
       
        新增小区
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
        <el-table-column prop="communityName" label="小区名称"/>
        <el-table-column prop="communityAddress" label="地址"/>
        <el-table-column prop="area" label="面积(㎡)" width="120"/>
        <el-table-column prop="totalBuildings" label="楼栋数" width="100" align="center"/>
        <el-table-column prop="developer" label="开发商"/>
        <el-table-column prop="propertyCompany" label="物业公司"/>
        <el-table-column prop="contactPhone" label="联系电话"/>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ scope.row.status === 1 ? '正常' : '停用' }}
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
      width="600px"
      @close="handleDialogClose"
    >
      <el-form 
        :model="form" 
        ref="formRef"
        :rules="rules"
        label-width="120px"
      >
        <el-form-item label="小区名称" prop="communityName">
          <el-input v-model="form.communityName" placeholder="请输入小区名称" />
        </el-form-item>
        <el-form-item label="小区地址" prop="communityAddress">
          <el-input v-model="form.communityAddress" placeholder="请输入小区地址" />
        </el-form-item>
        <el-form-item label="小区面积(㎡)">
          <el-input v-model.number="form.area" type="number" placeholder="请输入小区面积" />
        </el-form-item>
        <el-form-item label="楼栋数">
          <el-input v-model.number="form.totalBuildings" type="number" placeholder="请输入楼栋数" />
        </el-form-item>
        <el-form-item label="开发商">
          <el-input v-model="form.developer" placeholder="请输入开发商" />
        </el-form-item>
        <el-form-item label="物业公司">
          <el-input v-model="form.propertyCompany" placeholder="请输入物业公司" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
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
  getCommunityList,
  createCommunity,
  updateCommunity,
  deleteCommunity
} from '@/api/community'

// 搜索表单
const searchForm = reactive({
  communityName: '',
  status: ''
})

// 表格数据
const allData = ref([])  // 存储所有数据
const tableData = ref([])  // 搜索后的数据
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
  communityName: '',
  communityAddress: '',
  area: '',
  totalBuildings: '',
  developer: '',
  propertyCompany: '',
  contactPhone: '',
  status: 1,
  description: ''
})

// 表单验证规则
const rules = {
  communityName: [
    { required: true, message: '请输入小区名称', trigger: 'blur' }
  ],
  communityAddress: [
    { required: true, message: '请输入小区地址', trigger: 'blur' }
  ]
}

// 弹窗标题
const dialogTitle = computed(() => {
  return dialogType.value === 'add' ? '新增小区' : '编辑小区'
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
  
  // 小区名称模糊搜索
  if (searchForm.communityName) {
    filtered = filtered.filter(item => 
      item.communityName.toLowerCase().includes(searchForm.communityName.toLowerCase())
    )
  }
  
  // 状态精确搜索
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
  tableData.value = filtered
  pagination.total = tableData.value.length
  pagination.currentPage = 1
}

// 加载数据
const loadData = async () => {
  loading.value = true
  try {
    const res = await getCommunityList()
    // 后端返回的是数组，直接使用
    if (Array.isArray(res) && res.length > 0) {
      allData.value = res
    } else {
      // 使用模拟数据
      allData.value = [
        { id: 1, communityName: '阳光小区', communityAddress: '北京市朝阳区阳光路1号', area: 50000, totalBuildings: 10, developer: '阳光地产', propertyCompany: '阳光物业', contactPhone: '010-12345678', status: 1, createTime: '2026-01-01T09:00:00' },
        { id: 2, communityName: '幸福花园', communityAddress: '北京市海淀区幸福街88号', area: 80000, totalBuildings: 15, developer: '幸福集团', propertyCompany: '幸福物业', contactPhone: '010-87654321', status: 1, createTime: '2026-01-02T10:00:00' },
        { id: 3, communityName: '和谐家园', communityAddress: '北京市西城区和谐路66号', area: 60000, totalBuildings: 12, developer: '和谐地产', propertyCompany: '和谐物业', contactPhone: '010-24681357', status: 0, createTime: '2026-01-03T11:00:00' },
        { id: 4, communityName: '温馨家园', communityAddress: '北京市东城区温馨巷8号', area: 45000, totalBuildings: 8, developer: '温馨地产', propertyCompany: '温馨物业', contactPhone: '010-98765432', status: 1, createTime: '2026-01-04T14:00:00' }
      ]
    }
    
    updateTableData()
  } catch (error) {
    console.error('加载小区列表失败:', error)
    // 使用模拟数据
    allData.value = [
      { id: 1, communityName: '阳光小区', communityAddress: '北京市朝阳区阳光路1号', area: 50000, totalBuildings: 10, developer: '阳光地产', propertyCompany: '阳光物业', contactPhone: '010-12345678', status: 1, createTime: '2026-01-01T09:00:00' },
      { id: 2, communityName: '幸福花园', communityAddress: '北京市海淀区幸福街88号', area: 80000, totalBuildings: 15, developer: '幸福集团', propertyCompany: '幸福物业', contactPhone: '010-87654321', status: 1, createTime: '2026-01-02T10:00:00' },
      { id: 3, communityName: '和谐家园', communityAddress: '北京市西城区和谐路66号', area: 60000, totalBuildings: 12, developer: '和谐地产', propertyCompany: '和谐物业', contactPhone: '010-24681357', status: 0, createTime: '2026-01-03T11:00:00' },
      { id: 4, communityName: '温馨家园', communityAddress: '北京市东城区温馨巷8号', area: 45000, totalBuildings: 8, developer: '温馨地产', propertyCompany: '温馨物业', contactPhone: '010-98765432', status: 1, createTime: '2026-01-04T14:00:00' }
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
  searchForm.communityName = ''
  searchForm.status = ''
  updateTableData()
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
    Object.assign(form, { id: '', communityName: '', communityAddress: '', area: '', totalBuildings: '', developer: '', propertyCompany: '', contactPhone: '', status: 1, description: '' })
  } else if (type === 'edit' && row) {
    Object.assign(form, row)
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
      await createCommunity(form)
      ElMessage.success('新增成功')
    } else {
      await updateCommunity(form.id, form)
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
      '确定要删除该小区吗？',
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    await deleteCommunity(id)
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
.community-container {
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