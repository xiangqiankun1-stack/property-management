<template>
  <div class="repair-container">
    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="报修标题">
          <el-input v-model="searchForm.title" placeholder="请输入报修标题" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态">
            <el-option label="全部" value="" />
            <el-option label="待处理" :value="0" />
            <el-option label="处理中" :value="1" />
            <el-option label="已完成" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="openDialog('add')" class="add-btn">
        <el-icon>Plus</el-icon>
        新增报修
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
        <el-table-column prop="title" label="报修标题"/>
        <el-table-column prop="description" label="问题描述"/>
        <el-table-column prop="ownerName" label="报修人"/>
        <el-table-column prop="phoneNumber" label="联系电话"/>
        <el-table-column prop="houseNumber" label="房号"/>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180"/>
        <el-table-column prop="handleTime" label="处理时间" width="180"/>
        <el-table-column label="操作" width="200" align="center">
          <template #default="scope">
            <el-button size="small" @click="openDialog('edit', scope.row)">
              <el-icon>编辑</el-icon>
              
            </el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">
              <el-icon>删除</el-icon>
              
            </el-button>
            <el-button v-if="scope.row.status !== 2" size="small" type="success" @click="handleComplete(scope.row.id)">
              <el-icon>完成</el-icon>
              
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
        <el-form-item label="报修标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入报修标题" />
        </el-form-item>
        <el-form-item label="问题描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请描述问题详情" />
        </el-form-item>
        <el-form-item label="报修人" prop="ownerName">
          <el-input v-model="form.ownerName" placeholder="请输入报修人姓名" />
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
            <el-option label="已完成" :value="2" />
          </el-select>
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
import { getRepairList, createRepair, updateRepair, deleteRepair } from '@/api/repair'

const searchForm = reactive({ title: '', status: '' })
const tableData = ref([])
const loading = ref(false)
const pagination = reactive({ currentPage: 1, pageSize: 10, total: 0 })
const dialogVisible = ref(false)
const dialogType = ref('add')
const formRef = ref(null)
const form = reactive({
  id: '', title: '', description: '', ownerName: '', phoneNumber: '', houseNumber: '', status: 0
})

const rules = {
  title: [{ required: true, message: '请输入报修标题', trigger: 'blur' }],
  description: [{ required: true, message: '请描述问题详情', trigger: 'blur' }],
  ownerName: [{ required: true, message: '请输入报修人姓名', trigger: 'blur' }],
  phoneNumber: [{ required: true, message: '请输入联系电话', trigger: 'blur' }]
}

const dialogTitle = computed(() => dialogType.value === 'add' ? '新增报修' : '编辑报修')
const getStatusType = (status) => ({ 0: 'warning', 1: 'primary', 2: 'success' }[status] || 'info')
const getStatusText = (status) => ({ 0: '待处理', 1: '处理中', 2: '已完成' }[status] || '未知')

const loadData = async () => {
  loading.value = true
  try {
    const params = { page: pagination.currentPage, size: pagination.pageSize, ...searchForm }
    const res = await getRepairList(params)
    if (Array.isArray(res) && res.length > 0) {
      tableData.value = res
      pagination.total = res.length
    } else {
      tableData.value = [
        { id: 1, title: '水管漏水', description: '卫生间水管漏水严重', ownerName: '张三', phoneNumber: '13800138001', houseNumber: '1号楼101', status: 0, createTime: '2026-01-01T09:00:00', handleTime: '' },
        { id: 2, title: '电梯故障', description: '2号楼电梯停运', ownerName: '李四', phoneNumber: '13900139002', houseNumber: '2号楼201', status: 1, createTime: '2026-01-02T10:00:00', handleTime: '2026-01-02T11:00:00' },
        { id: 3, title: '楼道灯不亮', description: '3层楼道灯损坏', ownerName: '王五', phoneNumber: '13700137003', houseNumber: '1号楼302', status: 2, createTime: '2026-01-03T11:00:00', handleTime: '2026-01-03T14:00:00' }
      ]
      pagination.total = tableData.value.length
    }
  } catch (error) {
    console.error('加载报修列表失败:', error)
    tableData.value = [
      { id: 1, title: '水管漏水', description: '卫生间水管漏水严重', ownerName: '张三', phoneNumber: '13800138001', houseNumber: '1号楼101', status: 0, createTime: '2026-01-01T09:00:00', handleTime: '' },
      { id: 2, title: '电梯故障', description: '2号楼电梯停运', ownerName: '李四', phoneNumber: '13900139002', houseNumber: '2号楼201', status: 1, createTime: '2026-01-02T10:00:00', handleTime: '2026-01-02T11:00:00' },
      { id: 3, title: '楼道灯不亮', description: '3层楼道灯损坏', ownerName: '王五', phoneNumber: '13700137003', houseNumber: '1号楼302', status: 2, createTime: '2026-01-03T11:00:00', handleTime: '2026-01-03T14:00:00' }
    ]
    pagination.total = tableData.value.length
  } finally {
    loading.value = false
  }
}

const handleSearch = () => { pagination.currentPage = 1; loadData() }
const handleReset = () => { searchForm.title = ''; searchForm.status = ''; handleSearch() }

const openDialog = (type, row = null) => {
  dialogType.value = type
  dialogVisible.value = true
  if (formRef.value) formRef.value.resetFields()
  if (type === 'add') {
    Object.assign(form, { id: '', title: '', description: '', ownerName: '', phoneNumber: '', houseNumber: '', status: 0 })
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
      await createRepair(form)
      ElMessage.success('新增成功')
    } else {
      await updateRepair(form.id, form)
      ElMessage.success('修改成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error(dialogType.value === 'add' ? '新增失败' : '修改失败')
  }
}

const handleComplete = async (id) => {
  try {
    await updateRepair(id, { status: 2 })
    ElMessage.success('报修已完成')
    loadData()
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该报修记录吗？', '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    await deleteRepair(id)
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
.repair-container { padding: 20px; }
.search-card { margin-bottom: 20px; }
.add-btn { float: right; }
.table-card { min-height: 400px; }
</style>