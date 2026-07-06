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
/* 容器样式 */
.community-container {
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
  background: linear-gradient(90deg, #909399 0%, #409EFF 50%, #67C23A 100%);
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
  box-shadow: 0 6px 16px rgba(144, 147, 153, 0.3);
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
  color: #909399;
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
  background: #909399;
  color: white;
}

/* 弹窗样式 */
:deep(.el-dialog) {
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
}

/* 弹窗标题样式优化 */
:deep(.el-dialog__header) {
  background: linear-gradient(135deg, #a2a4a5 0%, #e1d1d1 100%);
  padding: 18px 24px;
  display: flex;
  justify-content: center;  /* ✅ 标题居中 */
  align-items: center;
  position: relative;
}

:deep(.el-dialog__title) {
  color: white;
  font-weight: 700;  /* ✅ 字体加粗 */
  font-size: 18px;  /* ✅ 稍微大一点 */
  letter-spacing: 1px;  /* ✅ 字间距 */
}

/* 关闭按钮（X）位置微调 */
:deep(.el-dialog__headerbtn) {
  position: absolute;
  right: 16px;
  top: 50%;
  transform: translateY(-50%);
  color: #333 !important;
  font-size: 20px;
}

:deep(.el-dialog__headerbtn:hover) {
  color: black;
}

:deep(.el-dialog__headerbtn .el-dialog__close) {
  font-weight: 1000;  /* ✅ X 按钮加粗 */
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
  box-shadow: 0 0 0 3px rgba(144, 147, 153, 0.1);
  border-color: #909399;
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

/* 日期选择器样式 */
:deep(.el-date-editor) {
  border-radius: 8px;
}

/* 文本域样式 */
:deep(.el-textarea__inner) {
  border-radius: 8px;
  font-size: 14px;
}
</style>