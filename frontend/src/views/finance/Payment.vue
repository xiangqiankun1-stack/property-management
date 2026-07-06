<template>
  <div class="payment-container">
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="缴费流水号">
          <el-input v-model="searchForm.paymentNo" placeholder="请输入缴费流水号" clearable />
        </el-form-item>
        <el-form-item label="支付方式">
          <el-select v-model="searchForm.payMethod" placeholder="请选择支付方式" clearable>
            <el-option label="全部" value="" />
            <el-option label="现金" :value="1" />
            <el-option label="微信" :value="2" />
            <el-option label="支付宝" :value="3" />
            <el-option label="银行卡" :value="4" />
            <el-option label="其他" :value="5" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
            <el-option label="全部" value="" />
            <el-option label="有效" :value="1" />
            <el-option label="已作废" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="业主ID">
          <el-input v-model.number="searchForm.ownerId" type="number" placeholder="请输入业主ID" clearable />
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
        
        新增缴费
      </el-button>
    </el-card>

    <el-card class="table-card">
      <div class="table-header">
        <div class="summary-info">
          <span>共 <strong>{{ pagination.total }}</strong> 条记录</span>
          <span class="divider">|</span>
          <span>总缴费金额: <strong>{{ totalAmount }}</strong> 元</span>
        </div>
      </div>
      
      <el-table :data="displayData" border stripe :loading="loading" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" align="center"/>
        <el-table-column prop="paymentNo" label="缴费流水号" min-width="150"/>
        <el-table-column prop="billName" label="账单名称"/>
        <el-table-column prop="payer" label="缴费人"/>
        <el-table-column prop="amount" label="金额(元)" width="120" align="right"/>
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
        <el-table-column label="操作" width="165" align="center">
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
       <!--  <el-form-item label="缴费流水号" prop="paymentNo">
          <el-input v-model="form.paymentNo" placeholder="请输入缴费流水号" />
        </el-form-item> -->
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
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, RefreshLeft, Plus, Delete } from '@element-plus/icons-vue'
import { getPaymentList, createPayment, deletePayment } from '@/api/payment'

const searchForm = reactive({ 
  paymentNo: '', 
  payMethod: '', 
  status: '',
  ownerId: ''
})
const allData = ref([])  // 存储所有数据
const tableData = ref([])  // 搜索后的数据
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

// 计算统计信息（基于搜索结果）
const totalAmount = computed(() => {
  return tableData.value.reduce((sum, item) => sum + (item.amount || 0), 0).toFixed(2)
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
  
  // 缴费流水号模糊搜索
  if (searchForm.paymentNo) {
    filtered = filtered.filter(item => 
      item.paymentNo.toLowerCase().includes(searchForm.paymentNo.toLowerCase())
    )
  }
  
  // 支付方式精确搜索
  if (searchForm.payMethod !== '') {
    filtered = filtered.filter(item => 
      item.payMethod === searchForm.payMethod
    )
  }
  
  // 状态精确搜索
  if (searchForm.status !== '') {
    filtered = filtered.filter(item => 
      item.status === searchForm.status
    )
  }
  
  // 业主ID精确搜索
  if (searchForm.ownerId) {
    filtered = filtered.filter(item => 
      item.ownerId === searchForm.ownerId
    )
  }
  
  return filtered
}

// 更新表格数据
const updateTableData = () => {
  const filtered = filterData()
  tableData.value = filtered.map(item => ({
    id: item.id,
    paymentNo: item.paymentNo,
    billName: `账单${item.billId}`,
    payer: `业主${item.ownerId}`,
    amount: item.payAmount,
    method: payMethodMap[item.payMethod] || '未知',
    payTime: item.payTime,
    transactionId: item.voucherNo || '-',
    status: item.status
  }))
  pagination.total = tableData.value.length
  pagination.currentPage = 1  // 搜索后回到第一页
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getPaymentList()
    
    if (Array.isArray(res) && res.length > 0) {
      // 存储原始数据
      allData.value = res
    } else {
      // 如果后端没有数据，使用模拟数据
      allData.value = [
        { id: 1, paymentNo: 'PAY202602100001', billId: 1, ownerId: 1, payAmount: 223.75, payMethod: 2, payTime: '2026-02-10T14:20:00', operatorId: 4, voucherNo: 'WX20260210142012345', status: 1 },
        { id: 2, paymentNo: 'PAY202602120001', billId: 3, ownerId: 2, payAmount: 150.00, payMethod: 1, payTime: '2026-02-12T09:00:00', operatorId: 4, voucherNo: null, status: 1 }
      ]
    }
    
    // 更新表格
    updateTableData()
  } catch (error) {
    console.error('加载缴费列表失败:', error)
    // 使用模拟数据
    allData.value = [
      { id: 1, paymentNo: 'PAY202602100001', billId: 1, ownerId: 1, payAmount: 223.75, payMethod: 2, payTime: '2026-02-10T14:20:00', operatorId: 4, voucherNo: 'WX20260210142012345', status: 1 },
      { id: 2, paymentNo: 'PAY202602120001', billId: 3, ownerId: 2, payAmount: 150.00, payMethod: 1, payTime: '2026-02-12T09:00:00', operatorId: 4, voucherNo: null, status: 1 }
    ]
    updateTableData()
  } finally {
    loading.value = false
  }
}

const handleSearch = () => { 
  updateTableData() 
}

const handleReset = () => { 
  searchForm.paymentNo = ''
  searchForm.payMethod = ''
  searchForm.status = ''
  searchForm.ownerId = ''
  updateTableData() 
}

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
    loadData()  // 重新加载数据
  } catch (error) {
    ElMessage.error('新增失败')
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该缴费记录吗？', '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    await deletePayment(id)
    ElMessage.success('删除成功')
    loadData()  // 重新加载数据
  } catch (error) {
    if (error !== 'cancel') ElMessage.error('删除失败')
  }
}

const handleSizeChange = (size) => { 
  pagination.pageSize = size 
  if (pagination.currentPage > Math.ceil(pagination.total / size)) {
    pagination.currentPage = 1
  }
}

const handleCurrentChange = (page) => { 
  pagination.currentPage = page 
}

onMounted(() => loadData())
</script>

<style scoped>
/* 容器样式 */
.payment-container {
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
  background: linear-gradient(90deg, #EB2F96 0%, #409EFF 50%, #67C23A 100%);
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
  box-shadow: 0 6px 16px rgba(64, 158, 255, 0.3);
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
  color: #EB2F96;
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
  background: #EB2F96;
  color: white;
}

/* 弹窗样式 */
:deep(.el-dialog) {
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
}

:deep(.el-dialog__header) {
  background: linear-gradient(135deg, #EB2F96 0%, #d91f82 100%);
  padding: 18px 24px;
}

:deep(.el-dialog__title) {
  color: white;
  font-weight: 600;
  font-size: 16px;
}

:deep(.el-dialog__headerbtn) {
  color: rgba(255, 255, 255, 0.8);
}

:deep(.el-dialog__headerbtn:hover) {
  color: white;
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
  box-shadow: 0 0 0 3px rgba(235, 47, 150, 0.1);
  border-color: #EB2F96;
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
</style>