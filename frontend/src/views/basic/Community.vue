<template>
  <div class="community-container">
    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="小区名称">
          <el-input v-model="searchForm.communityName" placeholder="请输入小区名称" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="openDialog('add')" class="add-btn">
        <el-icon>新增小区</el-icon>
        
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
import { ref, reactive, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getCommunityList,
  createCommunity,
  updateCommunity,
  deleteCommunity
} from '@/api/community'

// 搜索表单
const searchForm = reactive({
  communityName: ''
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
    const res = await getCommunityList(params)
    // 后端返回的是数组，直接使用
    if (Array.isArray(res) && res.length > 0) {
      tableData.value = res
      pagination.total = res.length
    } else {
      // 使用模拟数据
      tableData.value = [
        { id: 1, communityName: '阳光小区', communityAddress: '北京市朝阳区阳光路1号', area: 50000, totalBuildings: 10, developer: '阳光地产', propertyCompany: '阳光物业', contactPhone: '010-12345678', status: 1, createTime: '2026-01-01T09:00:00' },
        { id: 2, communityName: '幸福花园', communityAddress: '北京市海淀区幸福街88号', area: 80000, totalBuildings: 15, developer: '幸福集团', propertyCompany: '幸福物业', contactPhone: '010-87654321', status: 1, createTime: '2026-01-02T10:00:00' },
        { id: 3, communityName: '和谐家园', communityAddress: '北京市西城区和谐路66号', area: 60000, totalBuildings: 12, developer: '和谐地产', propertyCompany: '和谐物业', contactPhone: '010-24681357', status: 0, createTime: '2026-01-03T11:00:00' }
      ]
      pagination.total = tableData.value.length
    }
  } catch (error) {
    console.error('加载小区列表失败:', error)
    // 使用模拟数据
    tableData.value = [
      { id: 1, communityName: '阳光小区', communityAddress: '北京市朝阳区阳光路1号', area: 50000, totalBuildings: 10, developer: '阳光地产', propertyCompany: '阳光物业', contactPhone: '010-12345678', status: 1, createTime: '2026-01-01T09:00:00' },
      { id: 2, communityName: '幸福花园', communityAddress: '北京市海淀区幸福街88号', area: 80000, totalBuildings: 15, developer: '幸福集团', propertyCompany: '幸福物业', contactPhone: '010-87654321', status: 1, createTime: '2026-01-02T10:00:00' },
      { id: 3, communityName: '和谐家园', communityAddress: '北京市西城区和谐路66号', area: 60000, totalBuildings: 12, developer: '和谐地产', propertyCompany: '和谐物业', contactPhone: '010-24681357', status: 0, createTime: '2026-01-03T11:00:00' }
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
  searchForm.communityName = ''
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
    form.communityName = ''
    form.communityAddress = ''
    form.area = ''
    form.totalBuildings = ''
    form.developer = ''
    form.propertyCompany = ''
    form.contactPhone = ''
    form.status = 1
    form.description = ''
  } else if (type === 'edit' && row) {
    form.id = row.id
    form.communityName = row.communityName
    form.communityAddress = row.communityAddress
    form.area = row.area
    form.totalBuildings = row.totalBuildings
    form.developer = row.developer
    form.propertyCompany = row.propertyCompany
    form.contactPhone = row.contactPhone
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
.community-container {
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