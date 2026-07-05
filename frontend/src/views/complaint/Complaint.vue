<template>
  <div class="complaint-container">
    <!-- 搜索条件 -->
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="投诉内容">
          <el-input 
            v-model="searchForm.content" 
            placeholder="请输入投诉标题或内容"
            clearable
            @keyup.enter="loadComplaints"
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="searchForm.category" placeholder="请选择分类" clearable>
            <el-option label="物业服务" value="物业服务" />
            <el-option label="环境卫生" value="环境卫生" />
            <el-option label="安全隐患" value="安全隐患" />
            <el-option label="设施损坏" value="设施损坏" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
            <el-option label="待处理" :value="0" />
            <el-option label="处理中" :value="1" />
            <el-option label="已处理" :value="2" />
            <el-option label="已关闭" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadComplaints">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
          <el-button type="success" @click="handleAdd">新增投诉</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 数据表格 -->
    <el-card>
      <el-table 
        :data="tableData" 
        border 
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="id" label="ID" width="70" align="center" fixed="left" />
        
        <el-table-column prop="complaintNo" label="投诉编号" width="150" align="center" />
        
        <el-table-column prop="complaintTitle" label="投诉标题" width="180" show-overflow-tooltip />
        
        <el-table-column prop="complaintCategory" label="分类" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getCategoryType(row.complaintCategory)">
              {{ row.complaintCategory }}
            </el-tag>
          </template>
        </el-table-column>
        
        <el-table-column prop="complaintContent" label="投诉内容" min-width="200" show-overflow-tooltip />
        
        <el-table-column prop="contactPhone" label="联系电话" width="130" align="center" />
        
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        
        <el-table-column prop="createTime" label="创建时间" width="170" align="center">
          <template #default="{ row }">
            {{ formatTime(row.createTime) }}
          </template>
        </el-table-column>
        
        <el-table-column prop="handleTime" label="处理时间" width="170" align="center">
          <template #default="{ row }">
            {{ row.handleTime ? formatTime(row.handleTime) : '-' }}
          </template>
        </el-table-column>
        
        <el-table-column prop="handleResult" label="处理结果" width="180" show-overflow-tooltip>
          <template #default="{ row }">
            {{ row.handleResult || '-' }}
          </template>
        </el-table-column>
        
        <el-table-column prop="satisfaction" label="满意度" width="140" align="center">
          <template #default="{ row }">
            <el-rate 
              v-if="row.satisfaction"
              v-model="row.satisfaction" 
              disabled 
              :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
              :texts="['极差', '差', '一般', '好', '极好']"
              show-text
            />
            <span v-else>-</span>
          </template>
        </el-table-column>
        
        <el-table-column label="操作" width="260" align="center" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="viewDetail(row)">
              查看
            </el-button>
            <el-button 
              type="warning" 
              size="small" 
              @click="handleEdit(row)"
            >
              编辑
            </el-button>
            <el-button 
              type="success" 
              size="small" 
              @click="handleComplaint(row)"
              v-if="row.status === 0 || row.status === 1"
            >
              处理
            </el-button>
            <el-button 
              type="danger" 
              size="small" 
              @click="deleteComplaint(row)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-wrapper">
        <el-pagination
          v-model:page-size="pageInfo.size"
          v-model:current-page="pageInfo.page"
          :total="pageInfo.total"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="loadComplaints"
          @current-change="loadComplaints"
        />
      </div>
    </el-card>

    <!-- 查看详情对话框 -->
    <el-dialog 
      v-model="dialogVisible" 
      :title="dialogTitle"
      width="700px"
    >
      <el-descriptions :column="2" border>
        <el-descriptions-item label="投诉编号">
          {{ currentRow?.complaintNo }}
        </el-descriptions-item>
        <el-descriptions-item label="投诉标题">
          {{ currentRow?.complaintTitle }}
        </el-descriptions-item>
        <el-descriptions-item label="分类">
          <el-tag :type="getCategoryType(currentRow?.complaintCategory)">
            {{ currentRow?.complaintCategory }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="getStatusType(currentRow?.status)">
            {{ getStatusText(currentRow?.status) }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="投诉内容" :span="2">
          {{ currentRow?.complaintContent }}
        </el-descriptions-item>
        <el-descriptions-item label="联系电话">
          {{ currentRow?.contactPhone || '-' }}
        </el-descriptions-item>
        <el-descriptions-item label="满意度">
          <el-rate 
            v-if="currentRow?.satisfaction"
            v-model="currentRow.satisfaction" 
            disabled 
            :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
          />
          <span v-else>-</span>
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">
          {{ formatTime(currentRow?.createTime) }}
        </el-descriptions-item>
        <el-descriptions-item label="更新时间">
          {{ formatTime(currentRow?.updateTime) }}
        </el-descriptions-item>
        <el-descriptions-item label="处理时间" :span="2">
          {{ currentRow?.handleTime ? formatTime(currentRow.handleTime) : '-' }}
        </el-descriptions-item>
        <el-descriptions-item label="处理结果" :span="2">
          {{ currentRow?.handleResult || '-' }}
        </el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <el-button @click="dialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>

    <!-- 新增/编辑对话框 -->
    <el-dialog 
      v-model="formDialogVisible" 
      :title="formDialogTitle"
      width="600px"
    >
      <el-form 
        :model="formData" 
        :rules="formRules"
        ref="formRef"
        label-width="100px"
      >
        <el-form-item label="投诉标题" prop="complaintTitle">
          <el-input v-model="formData.complaintTitle" placeholder="请输入投诉标题" />
        </el-form-item>
        <el-form-item label="投诉分类" prop="complaintCategory">
          <el-select v-model="formData.complaintCategory" placeholder="请选择分类" style="width: 100%">
            <el-option label="物业服务" value="物业服务" />
            <el-option label="环境卫生" value="环境卫生" />
            <el-option label="安全隐患" value="安全隐患" />
            <el-option label="设施损坏" value="设施损坏" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="投诉内容" prop="complaintContent">
          <el-input 
            v-model="formData.complaintContent" 
            type="textarea"
            :rows="4"
            placeholder="请详细描述投诉内容"
          />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="formData.contactPhone" placeholder="请输入联系电话" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="formDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { 
  getComplaintList, 
  createComplaint, 
  updateComplaint, 
  deleteComplaint as deleteComplaintApi 
} from '@/api/complaint'

// ===== 搜索 =====
const searchForm = reactive({
  content: '',
  category: '',
  status: ''
})

// ===== 分页 =====
const pageInfo = reactive({
  page: 1,
  size: 10,
  total: 0
})

// ===== 表格 =====
const tableData = ref([])
const loading = ref(false)

// ===== 查看详情 =====
const dialogVisible = ref(false)
const dialogTitle = ref('投诉详情')
const currentRow = ref(null)

// ===== 新增/编辑 =====
const formDialogVisible = ref(false)
const formDialogTitle = ref('新增投诉')
const formRef = ref(null)
const isEdit = ref(false)

const formData = reactive({
  id: null,
  complaintTitle: '',
  complaintCategory: '',
  complaintContent: '',
  contactPhone: ''
})

const formRules = {
  complaintTitle: [{ required: true, message: '请输入投诉标题', trigger: 'blur' }],
  complaintCategory: [{ required: true, message: '请选择投诉分类', trigger: 'change' }],
  complaintContent: [{ required: true, message: '请输入投诉内容', trigger: 'blur' }],
  contactPhone: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ]
}

// ===== 加载数据 =====
// Complaint.vue 中的 loadComplaints 方法
const loadComplaints = async () => {
  loading.value = true
  try {
    const params = {
      page: pageInfo.page,
      size: pageInfo.size,
      content: searchForm.content,
      status: searchForm.status
    }
    
    const res = await getComplaintList(params)
    console.log('完整响应:', res)  // 打印完整响应
    
    // 关键：检查 res 的结构
    console.log('res 类型:', typeof res)
    console.log('res 是否是数组:', Array.isArray(res))
    console.log('res.data:', res?.data)
    
    // 根据实际数据结构处理
    if (Array.isArray(res)) {
      // 如果直接返回数组
      tableData.value = res
      pageInfo.total = res.length
    } else if (res && res.code === 200) {
      // 如果返回的是标准响应 { code, message, data }
      if (Array.isArray(res.data)) {
        tableData.value = res.data
        pageInfo.total = res.data.length
      } else if (res.data && Array.isArray(res.data.list)) {
        tableData.value = res.data.list
        pageInfo.total = res.data.total || res.data.list.length
      } else {
        tableData.value = []
        pageInfo.total = 0
      }
    } else if (res && res.data && Array.isArray(res.data)) {
      // 如果返回的是 { data: [...] }
      tableData.value = res.data
      pageInfo.total = res.data.length
    } else {
      tableData.value = []
      pageInfo.total = 0
    }
    
    console.log('最终 tableData:', tableData.value)  // 打印最终数据
    console.log('数据条数:', tableData.value.length)
    
  } catch (error) {
    console.error('加载失败:', error)
    ElMessage.error('加载投诉列表失败')
  } finally {
    loading.value = false
  }
}

// ===== 重置搜索 =====
const resetSearch = () => {
  searchForm.content = ''
  searchForm.category = ''
  searchForm.status = ''
  pageInfo.page = 1
  loadComplaints()
}

// ===== 状态转换 =====
const getStatusText = (status) => {
  const map = {
    0: '待处理',
    1: '处理中',
    2: '已处理',
    3: '已关闭'
  }
  return map[status] || '未知'
}

const getStatusType = (status) => {
  const map = {
    0: 'danger',
    1: 'warning',
    2: 'success',
    3: 'info'
  }
  return map[status] || ''
}

const getCategoryType = (category) => {
  const map = {
    '物业服务': 'primary',
    '环境卫生': 'success',
    '安全隐患': 'danger',
    '设施损坏': 'warning',
    '其他': 'info'
  }
  return map[category] || ''
}

// ===== 格式化时间 =====
const formatTime = (time) => {
  if (!time) return '-'
  try {
    const date = new Date(time)
    return date.toLocaleString('zh-CN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit'
    })
  } catch {
    return time
  }
}

// ===== 查看详情 =====
const viewDetail = (row) => {
  currentRow.value = row
  dialogTitle.value = '投诉详情'
  dialogVisible.value = true
}

// ===== 新增投诉 =====
const handleAdd = () => {
  isEdit.value = false
  formDialogTitle.value = '新增投诉'
  formData.id = null
  formData.complaintTitle = ''
  formData.complaintCategory = ''
  formData.complaintContent = ''
  formData.contactPhone = ''
  formDialogVisible.value = true
}

// ===== 编辑投诉 =====
const handleEdit = (row) => {
  isEdit.value = true
  formDialogTitle.value = '编辑投诉'
  formData.id = row.id
  formData.complaintTitle = row.complaintTitle
  formData.complaintCategory = row.complaintCategory
  formData.complaintContent = row.complaintContent
  formData.contactPhone = row.contactPhone || ''
  formDialogVisible.value = true
}

// ===== 提交表单 =====
const submitForm = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    
    try {
      const submitData = {
        complaintTitle: formData.complaintTitle,
        complaintCategory: formData.complaintCategory,
        complaintContent: formData.complaintContent,
        contactPhone: formData.contactPhone,
        complaintType: 1,
        status: 0
      }
      
      if (isEdit.value) {
        await updateComplaint(formData.id, submitData)
        ElMessage.success('更新成功')
      } else {
        await createComplaint(submitData)
        ElMessage.success('新增成功')
      }
      
      formDialogVisible.value = false
      loadComplaints()
    } catch (error) {
      console.error('提交失败:', error)
      ElMessage.error(isEdit.value ? '更新失败' : '新增失败')
    }
  })
}

// ===== 处理投诉 =====
const handleComplaint = (row) => {
  // TODO: 弹出处理对话框
  ElMessage.info('处理功能开发中...')
}

// ===== 删除投诉 =====
const deleteComplaint = (row) => {
  ElMessageBox.confirm(
    `确定要删除投诉 "${row.complaintTitle}" 吗？`,
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await deleteComplaintApi(row.id)
      ElMessage.success('删除成功')
      loadComplaints()
    } catch (error) {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }).catch(() => {})
}

// ===== 初始化 =====
onMounted(() => {
  loadComplaints()
})
</script>

<style scoped>
.complaint-container {
  padding: 20px;
}
.search-card {
  margin-bottom: 20px;
}
.pagination-wrapper {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>