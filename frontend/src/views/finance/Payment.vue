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
            <el-option label="现金" value="现金" />
            <el-option label="微信" value="微信" />
            <el-option label="支付宝" value="支付宝" />
            <el-option label="银行卡" value="银行卡" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="openDialog('add')" class="add-btn">
        新增缴费
      </el-button>
    </el-card>

    <el-card class="table-card">
      <el-table :data="tableData" border stripe :loading="loading" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" align="center"/>
        <el-table-column prop="paymentNo" label="缴费流水号"/>
        <el-table-column prop="billName" label="账单名称"/>
        <el-table-column prop="payer" label="缴费人"/>
        <el-table-column prop="amount" label="金额(元)" width="120"/>
        <el-table-column prop="method" label="支付方式" width="120"/>
        <el-table-column prop="payTime" label="支付时间" width="180"/>
        <el-table-column prop="transactionId" label="交易单号"/>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'warning'">
              {{ scope.row.status === 1 ? '有效' : '已作废' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template #default="scope">
            <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">
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
        <el-form-item label="缴费流水号" prop="paymentNo">
          <el-input v-model="form.paymentNo" placeholder="请输入缴费流水号" />
        </el-form-item>
        <el-form-item label="账单ID" prop="billId">
          <el-input v-model.number="form.billId" type="number" placeholder="请输入账单ID" />
        </el-form-item>
        <el-form-item label="业主ID" prop="ownerId">
          <el-input v-model.number="form.ownerId" type="number" placeholder="请输入业主ID" />
        </el-form-item>
        <el-form-item label="金额(元)" prop="payAmount">
          <el-input v-model.number="form.payAmount" type="number" placeholder="请输入缴费金额" />
        </el-form-item>
        <el-form-item label="支付方式" prop="payMethod">
          <el-select v-model="form.payMethod" placeholder="请选择支付方式">
            <el-option label="现金" :value="1" />
            <el-option label="微信" :value="2" />
            <el-option label="支付宝" :value="3" />
            <el-option label="银行卡" :value="4" />
            <el-option label="其他" :value="5" />
          </el-select>
        </el-form-item>
        <el-form-item label="缴费时间" prop="payTime">
          <el-date-picker v-model="form.payTime" type="datetime" placeholder="请选择缴费时间" />
        </el-form-item>
        <el-form-item label="操作员ID" prop="operatorId">
          <el-input v-model.number="form.operatorId" type="number" placeholder="请输入操作员ID" />
        </el-form-item>
        <el-form-item label="凭证号" prop="voucherNo">
          <el-input v-model="form.voucherNo" placeholder="请输入第三方支付流水号" />
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
const form = reactive({ 
  paymentNo: '', 
  billId: '', 
  ownerId: '', 
  payAmount: '', 
  payMethod: 2, 
  payTime: '', 
  operatorId: '', 
  voucherNo: '',
  status: 1 
})

const rules = {
  paymentNo: [{ required: true, message: '请输入缴费流水号', trigger: 'blur' }],
  billId: [{ required: true, message: '请输入账单ID', trigger: 'blur' }, { type: 'number', message: '必须是数字', trigger: 'blur' }],
  ownerId: [{ required: true, message: '请输入业主ID', trigger: 'blur' }, { type: 'number', message: '必须是数字', trigger: 'blur' }],
  payAmount: [{ required: true, message: '请输入缴费金额', trigger: 'blur' }, { type: 'number', min: 0.01, message: '金额必须大于0', trigger: 'blur' }],
  payMethod: [{ required: true, message: '请选择支付方式', trigger: 'blur' }],
  payTime: [{ required: true, message: '请选择缴费时间', trigger: 'blur' }],
  operatorId: [{ required: true, message: '请输入操作员ID', trigger: 'blur' }, { type: 'number', message: '必须是数字', trigger: 'blur' }]
}

// 支付方式映射
const payMethodMap = {
  1: '现金',
  2: '微信',
  3: '支付宝',
  4: '银行卡',
  5: '其他'
}

const loadData = async () => {
  loading.value = true
  try {
    const params = { page: pagination.currentPage, size: pagination.pageSize, ...searchForm }
    const res = await getPaymentList(params)
    
    if (Array.isArray(res) && res.length > 0) {
      // 转换后端数据为前端格式
      tableData.value = res.map(item => ({
        id: item.id,
        paymentNo: item.paymentNo,
        billName: `账单${item.billId}`,  // 实际项目中需要调用账单接口获取名称
        payer: `业主${item.ownerId}`,    // 实际项目中需要调用业主接口获取姓名
        amount: item.payAmount,
        method: payMethodMap[item.payMethod] || '未知',
        payTime: item.payTime,
        transactionId: item.voucherNo || '-',
        status: item.status
      }))
      pagination.total = res.length
    } else {
      tableData.value = []
      pagination.total = 0
    }
  } catch (error) {
    console.error('加载缴费列表失败:', error)
    tableData.value = []
    pagination.total = 0
  } finally {
    loading.value = false
  }
}

const handleSearch = () => { pagination.currentPage = 1; loadData() }
const handleReset = () => { searchForm.payer = ''; searchForm.method = ''; handleSearch() }

const openDialog = () => {
  dialogVisible.value = true
  if (formRef.value) formRef.value.resetFields()
  Object.assign(form, { paymentNo: '', billId: '', ownerId: '', payAmount: '', payMethod: 2, payTime: '', operatorId: '', voucherNo: '', status: 1 })
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