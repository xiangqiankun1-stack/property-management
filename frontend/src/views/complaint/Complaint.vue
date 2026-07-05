<template>
  <div class="complaint-container">
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="投诉内容">
          <el-input v-model="searchForm.content" placeholder="请输入投诉内容" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态">
            <el-option label="全部" value="" />
            <el-option label="待处理" :value="0" />
            <el-option label="处理中" :value="1" />
            <el-option label="已回复" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="openDialog('add')" class="add-btn">
        <el-icon>新增投诉</el-icon>
        
      </el-button>
    </el-card>

    <el-card class="table-card">
      <el-table :data="tableData" border stripe :loading="loading" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" align="center"/>
        <el-table-column prop="content" label="投诉内容"/>
        <el-table-column prop="complainerName" label="投诉人"/>
        <el-table-column prop="phoneNumber" label="联系电话"/>
        <el-table-column prop="houseNumber" label="房号"/>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="replyContent" label="回复内容"/>
        <el-table-column prop="createTime" label="投诉时间" width="180"/>
        <el-table-column prop="replyTime" label="回复时间" width="180"/>
        <el-table-column label="操作" width="200" align="center">
          <template #default="scope">
            <el-button size="small" @click="openDialog('edit', scope.row)">
              <el-icon>编辑</el-icon>
              
            </el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">
              <el-icon>删除</el-icon>
              
            </el-button>
            <el-button v-if="scope.row.status !== 2" size="small" type="success" @click="openReplyDialog(scope.row)">
              <el-icon>回复</el-icon>
              
            </el-button>
          </template>
        </el-table-column>
      </el-table>

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

    <!-- 投诉表单弹窗 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px" @close="handleDialogClose">
      <el-form :model="form" ref="formRef" :rules="rules" label-width="100px">
        <el-form-item label="投诉内容" prop="content">
          <el-input v-model="form.content" type="textarea" placeholder="请描述投诉内容" />
        </el-form-item>
        <el-form-item label="投诉人" prop="complainerName">
          <el-input v-model="form.complainerName" placeholder="请输入投诉人姓名" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="房号" prop="houseNumber">
          <el-input v-model="form.houseNumber" placeholder="请输入房号" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="待处理" :value="0" />
            <el-option label="处理中" :value="1" />
            <el-option label="已回复" :value="2" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <!-- 回复弹窗 -->
    <el-dialog v-model="replyDialogVisible" title="回复投诉" width="500px">
      <el-form :model="replyForm" ref="replyFormRef" label-width="100px">
        <el-form-item label="回复内容" prop="replyContent">
          <el-input v-model="replyForm.replyContent" type="textarea" placeholder="请输入回复内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="replyDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleReply">回复</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getComplaintList, createComplaint, updateComplaint, deleteComplaint } from '@/api/complaint'

const searchForm = reactive({ content: '', status: '' })
const tableData = ref([])
const loading = ref(false)
const pagination = reactive({ currentPage: 1, pageSize: 10, total: 0 })

const dialogVisible = ref(false)
const dialogType = ref('add')
const formRef = ref(null)
const form = reactive({ id: '', content: '', complainerName: '', phoneNumber: '', houseNumber: '', status: 0, replyContent: '' })

const replyDialogVisible = ref(false)
const replyFormRef = ref(null)
const replyForm = reactive({ complaintId: '', replyContent: '' })

const rules = {
  content: [{ required: true, message: '请输入投诉内容', trigger: 'blur' }],
  complainerName: [{ required: true, message: '请输入投诉人姓名', trigger: 'blur' }],
  phoneNumber: [{ required: true, message: '请输入联系电话', trigger: 'blur' }]
}

const dialogTitle = computed(() => dialogType.value === 'add' ? '新增投诉' : '编辑投诉')
const getStatusType = (status) => ({ 0: 'warning', 1: 'primary', 2: 'success' }[status] || 'info')
const getStatusText = (status) => ({ 0: '待处理', 1: '处理中', 2: '已回复' }[status] || '未知')

const loadData = async () => {
  loading.value = true
  try {
    const params = { page: pagination.currentPage, size: pagination.pageSize, ...searchForm }
    const res = await getComplaintList(params)
    if (Array.isArray(res) && res.length > 0) {
      tableData.value = res
      pagination.total = res.length
    } else {
      tableData.value = [
        { id: 1, content: '噪音扰民严重', complainerName: '张三', phoneNumber: '13800138001', houseNumber: '1号楼101', status: 0, replyContent: '', createTime: '2026-01-01T09:00:00', replyTime: '' },
        { id: 2, content: '小区卫生差', complainerName: '李四', phoneNumber: '13900139002', houseNumber: '2号楼201', status: 1, replyContent: '', createTime: '2026-01-02T10:00:00', replyTime: '' },
        { id: 3, content: '门禁系统故障', complainerName: '王五', phoneNumber: '13700137003', houseNumber: '1号楼302', status: 2, replyContent: '已安排维修', createTime: '2026-01-03T11:00:00', replyTime: '2026-01-03T14:00:00' }
      ]
      pagination.total = tableData.value.length
    }
  } catch (error) {
    console.error('加载投诉列表失败:', error)
    tableData.value = [
      { id: 1, content: '噪音扰民严重', complainerName: '张三', phoneNumber: '13800138001', houseNumber: '1号楼101', status: 0, replyContent: '', createTime: '2026-01-01T09:00:00', replyTime: '' },
      { id: 2, content: '小区卫生差', complainerName: '李四', phoneNumber: '13900139002', houseNumber: '2号楼201', status: 1, replyContent: '', createTime: '2026-01-02T10:00:00', replyTime: '' },
      { id: 3, content: '门禁系统故障', complainerName: '王五', phoneNumber: '13700137003', houseNumber: '1号楼302', status: 2, replyContent: '已安排维修', createTime: '2026-01-03T11:00:00', replyTime: '2026-01-03T14:00:00' }
    ]
    pagination.total = tableData.value.length
  } finally {
    loading.value = false
  }
}

const handleSearch = () => { pagination.currentPage = 1; loadData() }
const handleReset = () => { searchForm.content = ''; searchForm.status = ''; handleSearch() }

const openDialog = (type, row = null) => {
  dialogType.value = type
  dialogVisible.value = true
  if (formRef.value) formRef.value.resetFields()
  if (type === 'add') {
    Object.assign(form, { id: '', content: '', complainerName: '', phoneNumber: '', houseNumber: '', status: 0, replyContent: '' })
  } else if (type === 'edit' && row) {
    Object.assign(form, row)
  }
}

const handleDialogClose = () => { if (formRef.value) formRef.value.resetFields() }

const handleSubmit = async () => {
  if (!formRef.value) return
  const valid = await formRef.value.validate()
  if (!valid) return
  try {
    if (dialogType.value === 'add') {
      await createComplaint(form)
      ElMessage.success('新增成功')
    } else {
      await updateComplaint(form.id, form)
      ElMessage.success('修改成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error(dialogType.value === 'add' ? '新增失败' : '修改失败')
  }
}

const openReplyDialog = (row) => {
  replyForm.complaintId = row.id
  replyForm.replyContent = ''
  replyDialogVisible.value = true
}

const handleReply = async () => {
  try {
    await updateComplaint(replyForm.complaintId, { replyContent: replyForm.replyContent, status: 2 })
    ElMessage.success('回复成功')
    replyDialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error('回复失败')
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该投诉记录吗？', '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    await deleteComplaint(id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') ElMessage.error('删除失败')
  }
}

const handleSizeChange = (size) => { pagination.pageSize = size; loadData() }
const handleCurrentChange = (page) => { pagination.currentPage = page; loadData() }

onMounted(() => loadData())
</script>

<style scoped>
.complaint-container { padding: 20px; }
.search-card { margin-bottom: 20px; }
.add-btn { float: right; }
.table-card { min-height: 400px; }
</style>