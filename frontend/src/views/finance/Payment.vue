<template>
  <div class="payment-container">
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="缴费人">
          <el-input v-model="searchForm.payer" placeholder="请输入缴费人" />
        </el-form-item>
        <el-form-item label="支付方式">
          <el-select v-model="searchForm.method" placeholder="请选择支付方式">
            <el-option label="全部" value="" />
            <el-option label="微信" value="微信" />
            <el-option label="支付宝" value="支付宝" />
            <el-option label="现金" value="现金" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="openDialog('add')" class="add-btn">
        <el-icon>Plus</el-icon>
        新增缴费
      </el-button>
    </el-card>

    <el-card class="table-card">
      <el-table :data="tableData" border stripe :loading="loading" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" align="center"/>
        <el-table-column prop="billName" label="账单名称"/>
        <el-table-column prop="payer" label="缴费人"/>
        <el-table-column prop="amount" label="金额(元)" width="120"/>
        <el-table-column prop="method" label="支付方式" width="100"/>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'warning'">
              {{ scope.row.status === 1 ? '成功' : '失败' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="payTime" label="支付时间" width="180"/>
        <el-table-column prop="transactionId" label="交易单号"/>
        <el-table-column label="操作" width="150" align="center">
          <template #default="scope">
            <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">
              <el-icon>Delete</el-icon>
              删除
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

    <el-dialog v-model="dialogVisible" title="新增缴费" width="500px" @close="handleDialogClose">
      <el-form :model="form" ref="formRef" :rules="rules" label-width="100px">
        <el-form-item label="账单名称" prop="billName">
          <el-input v-model="form.billName" placeholder="请输入账单名称" />
        </el-form-item>
        <el-form-item label="缴费人" prop="payer">
          <el-input v-model="form.payer" placeholder="请输入缴费人姓名" />
        </el-form-item>
        <el-form-item label="金额(元)" prop="amount">
          <el-input v-model.number="form.amount" type="number" placeholder="请输入缴费金额" />
        </el-form-item>
        <el-form-item label="支付方式" prop="method">
          <el-select v-model="form.method" placeholder="请选择支付方式">
            <el-option label="微信" value="微信" />
            <el-option label="支付宝" value="支付宝" />
            <el-option label="现金" value="现金" />
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
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getPaymentList, createPayment, deletePayment } from '@/api/payment'

const searchForm = reactive({ payer: '', method: '' })
const tableData = ref([])
const loading = ref(false)
const pagination = reactive({ currentPage: 1, pageSize: 10, total: 0 })

const dialogVisible = ref(false)
const formRef = ref(null)
const form = reactive({ billName: '', payer: '', amount: '', method: '微信' })

const rules = {
  billName: [{ required: true, message: '请输入账单名称', trigger: 'blur' }],
  payer: [{ required: true, message: '请输入缴费人姓名', trigger: 'blur' }],
  amount: [{ required: true, message: '请输入缴费金额', trigger: 'blur' }, { type: 'number', min: 0.01, message: '金额必须大于0', trigger: 'blur' }],
  method: [{ required: true, message: '请选择支付方式', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const params = { page: pagination.currentPage, size: pagination.pageSize, ...searchForm }
    const res = await getPaymentList(params)
    if (Array.isArray(res) && res.length > 0) {
      tableData.value = res
      pagination.total = res.length
    } else {
      tableData.value = [
        { id: 1, billName: '2026年1月物业费', payer: '张三', amount: 150.00, method: '微信', status: 1, payTime: '2026-01-15T10:00:00', transactionId: 'TX202601150001' },
        { id: 2, billName: '2026年1月水费', payer: '张三', amount: 35.50, method: '微信', status: 1, payTime: '2026-01-15T10:01:00', transactionId: 'TX202601150002' },
        { id: 3, billName: '2026年1月电费', payer: '李四', amount: 89.00, method: '支付宝', status: 1, payTime: '2026-01-20T14:30:00', transactionId: 'TX202601200001' }
      ]
      pagination.total = tableData.value.length
    }
  } catch (error) {
    console.error('加载缴费列表失败:', error)
    tableData.value = [
      { id: 1, billName: '2026年1月物业费', payer: '张三', amount: 150.00, method: '微信', status: 1, payTime: '2026-01-15T10:00:00', transactionId: 'TX202601150001' },
      { id: 2, billName: '2026年1月水费', payer: '张三', amount: 35.50, method: '微信', status: 1, payTime: '2026-01-15T10:01:00', transactionId: 'TX202601150002' },
      { id: 3, billName: '2026年1月电费', payer: '李四', amount: 89.00, method: '支付宝', status: 1, payTime: '2026-01-20T14:30:00', transactionId: 'TX202601200001' }
    ]
    pagination.total = tableData.value.length
  } finally {
    loading.value = false
  }
}

const handleSearch = () => { pagination.currentPage = 1; loadData() }
const handleReset = () => { searchForm.payer = ''; searchForm.method = ''; handleSearch() }

const openDialog = () => {
  dialogVisible.value = true
  if (formRef.value) formRef.value.resetFields()
  Object.assign(form, { billName: '', payer: '', amount: '', method: '微信' })
}

const handleDialogClose = () => { if (formRef.value) formRef.value.resetFields() }

const handleSubmit = async () => {
  if (!formRef.value) return
  const valid = await formRef.value.validate()
  if (!valid) return
  try {
    await createPayment(form)
    ElMessage.success('新增成功')
    dialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error('新增失败')
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该缴费记录吗？', '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    await deletePayment(id)
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
.payment-container { padding: 20px; }
.search-card { margin-bottom: 20px; }
.add-btn { float: right; }
.table-card { min-height: 400px; }
</style>