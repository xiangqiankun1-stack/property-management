<template>
  <div class="bill-container">
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="账单编号">
          <el-input v-model="searchForm.billNo" placeholder="请输入账单编号" clearable />
        </el-form-item>
        <el-form-item label="费用类型">
          <el-select v-model="searchForm.feeItemId" placeholder="请选择费用类型" clearable>
            <el-option label="全部" value="" />
            <el-option label="物业费" :value="1" />
            <el-option label="水费" :value="2" />
            <el-option label="电费" :value="3" />
            <el-option label="燃气费" :value="4" />
            <el-option label="停车费" :value="5" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
            <el-option label="全部" value="" />
            <el-option label="待支付" :value="0" />
            <el-option label="部分支付" :value="1" />
            <el-option label="已支付" :value="2" />
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
        
        新增账单
      </el-button>
    </el-card>

    <el-card class="table-card">
      <div class="table-header">
        <div class="summary-info">
          <span>共 <strong>{{ pagination.total }}</strong> 条记录</span>
          <span class="divider">|</span>
          <span>总金额: <strong>{{ totalAmount }}</strong> 元</span>
          <span class="divider">|</span>
          <span>已支付: <strong>{{ paidTotal }}</strong> 元</span>
          <span class="divider">|</span>
          <span>待收: <strong>{{ unpaidTotal }}</strong> 元</span>
        </div>
      </div>
      
      <el-table :data="displayData" border stripe :loading="loading" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" align="center"/>
        <el-table-column prop="billNo" label="账单编号" min-width="150"/>
        <el-table-column prop="houseNumber" label="房号" width="100"/>
        <el-table-column prop="ownerName" label="业主" width="100"/>
        <el-table-column prop="feeItemName" label="费用类型" width="120"/>
        <el-table-column prop="period" label="计费周期" width="180"/>
        <el-table-column prop="amount" label="金额(元)" width="100" align="right"/>
        <el-table-column prop="paidAmount" label="已付(元)" width="100" align="right"/>
        <el-table-column prop="unpaidAmount" label="未付(元)" width="100" align="right"/>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="dueDate" label="到期日期" width="120"/>
        <el-table-column prop="generateTime" label="生成时间" width="180"/>
        <el-table-column label="操作" width="165" align="center">
          <template #default="scope">
            <el-button size="small" @click="openDialog('edit', scope.row)">
              
              编辑
            </el-button>
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

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px" @close="handleDialogClose">
      <el-form :model="form" ref="formRef" :rules="rules" label-width="100px">
        <el-form-item label="账单编号" prop="billNo">
          <el-input v-model="form.billNo" placeholder="请输入账单编号" />
        </el-form-item>
        <el-form-item label="房屋ID" prop="houseId">
          <el-input v-model.number="form.houseId" type="number" placeholder="请输入房屋ID" />
        </el-form-item>
        <el-form-item label="业主ID" prop="ownerId">
          <el-input v-model.number="form.ownerId" type="number" placeholder="请输入业主ID" />
        </el-form-item>
        <el-form-item label="费用类型" prop="feeItemId">
          <el-select v-model="form.feeItemId" placeholder="请选择费用类型">
            <el-option label="物业费" :value="1" />
            <el-option label="水费" :value="2" />
            <el-option label="电费" :value="3" />
            <el-option label="燃气费" :value="4" />
            <el-option label="停车费" :value="5" />
          </el-select>
        </el-form-item>
        <el-form-item label="计费周期开始" prop="periodStart">
          <el-date-picker v-model="form.periodStart" type="date" placeholder="请选择开始日期" />
        </el-form-item>
        <el-form-item label="计费周期结束" prop="periodEnd">
          <el-date-picker v-model="form.periodEnd" type="date" placeholder="请选择结束日期" />
        </el-form-item>
        <el-form-item label="金额(元)" prop="amount">
          <el-input v-model.number="form.amount" type="number" placeholder="请输入金额" />
        </el-form-item>
        <el-form-item label="到期日期" prop="dueDate">
          <el-date-picker v-model="form.dueDate" type="date" placeholder="请选择到期日期" />
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
import { getBillList, createBill, updateBill, deleteBill } from '@/api/bill'

const searchForm = reactive({ 
  billNo: '', 
  feeItemId: '', 
  status: '',
  ownerId: ''
})
const allData = ref([])  // 存储所有数据
const tableData = ref([])  // 搜索后的数据
const loading = ref(false)
const pagination = reactive({ currentPage: 1, pageSize: 10, total: 0 })

const dialogVisible = ref(false)
const dialogType = ref('add')
const formRef = ref(null)
const form = reactive({ 
  id: '', 
  billNo: '', 
  houseId: '', 
  ownerId: '', 
  feeItemId: 1, 
  periodStart: '', 
  periodEnd: '', 
  amount: '', 
  dueDate: '',
  paidAmount: 0,
  status: 0 
})

const rules = {
  billNo: [{ required: true, message: '请输入账单编号', trigger: 'blur' }],
  houseId: [{ required: true, message: '请输入房屋ID', trigger: 'blur' }, { type: 'number', message: '必须是数字', trigger: 'blur' }],
  ownerId: [{ required: true, message: '请输入业主ID', trigger: 'blur' }, { type: 'number', message: '必须是数字', trigger: 'blur' }],
  feeItemId: [{ required: true, message: '请选择费用类型', trigger: 'blur' }],
  periodStart: [{ required: true, message: '请选择计费周期开始', trigger: 'blur' }],
  periodEnd: [{ required: true, message: '请选择计费周期结束', trigger: 'blur' }],
  amount: [{ required: true, message: '请输入金额', trigger: 'blur' }, { type: 'number', min: 0.01, message: '金额必须大于0', trigger: 'blur' }],
  dueDate: [{ required: true, message: '请选择到期日期', trigger: 'blur' }]
}

// 费用类型映射
const feeItemMap = {
  1: '物业费',
  2: '水费',
  3: '电费',
  4: '燃气费',
  5: '停车费'
}

// 状态映射
const statusMap = {
  0: { text: '待支付', type: 'warning' },
  1: { text: '部分支付', type: 'info' },
  2: { text: '已支付', type: 'success' }
}

const getStatusText = (status) => {
  return statusMap[status]?.text || '未知'
}

const getStatusType = (status) => {
  return statusMap[status]?.type || 'default'
}

// 计算统计信息（基于搜索结果）
const totalAmount = computed(() => {
  return tableData.value.reduce((sum, item) => sum + (item.amount || 0), 0).toFixed(2)
})

const paidTotal = computed(() => {
  return tableData.value.reduce((sum, item) => sum + (item.paidAmount || 0), 0).toFixed(2)
})

const unpaidTotal = computed(() => {
  return (parseFloat(totalAmount.value) - parseFloat(paidTotal.value)).toFixed(2)
})

// 当前页显示的数据
const displayData = computed(() => {
  const start = (pagination.currentPage - 1) * pagination.pageSize
  const end = start + pagination.pageSize
  return tableData.value.slice(start, end)
})

const dialogTitle = computed(() => dialogType.value === 'add' ? '新增账单' : '编辑账单')

// 前端搜索过滤
const filterData = () => {
  let filtered = [...allData.value]
  
  // 账单编号模糊搜索
  if (searchForm.billNo) {
    filtered = filtered.filter(item => 
      item.billNo.toLowerCase().includes(searchForm.billNo.toLowerCase())
    )
  }
  
  // 费用类型精确搜索
  if (searchForm.feeItemId !== '') {
    filtered = filtered.filter(item => 
      item.feeItemId === searchForm.feeItemId
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
    billNo: item.billNo,
    houseNumber: `房屋${item.houseId}`,
    ownerName: `业主${item.ownerId}`,
    feeItemName: feeItemMap[item.feeItemId] || '未知',
    period: `${item.periodStart} ~ ${item.periodEnd}`,
    amount: item.amount,
    paidAmount: item.paidAmount,
    unpaidAmount: (item.amount - item.paidAmount).toFixed(2),
    status: item.status,
    dueDate: item.dueDate,
    generateTime: item.generateTime
  }))
  pagination.total = tableData.value.length
  pagination.currentPage = 1  // 搜索后回到第一页
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getBillList()
    
    if (Array.isArray(res) && res.length > 0) {
      // 存储原始数据
      allData.value = res
    } else {
      // 如果后端没有数据，使用模拟数据
      allData.value = [
        { id: 1, billNo: 'BL202602010001', houseId: 1, ownerId: 1, feeItemId: 1, periodStart: '2026-02-01', periodEnd: '2026-02-28', amount: 223.75, paidAmount: 223.75, dueDate: '2026-02-15', generateTime: '2026-02-01T00:10:00', status: 2 },
        { id: 2, billNo: 'BL202602010002', houseId: 2, ownerId: 2, feeItemId: 1, periodStart: '2026-02-01', periodEnd: '2026-02-28', amount: 300.00, paidAmount: 0.00, dueDate: '2026-02-15', generateTime: '2026-02-01T00:10:00', status: 0 },
        { id: 3, billNo: 'BL202602010003', houseId: 2, ownerId: 2, feeItemId: 2, periodStart: '2026-02-01', periodEnd: '2026-02-28', amount: 300.00, paidAmount: 150.00, dueDate: '2026-02-15', generateTime: '2026-02-01T00:10:00', status: 1 }
      ]
    }
    
    // 更新表格
    updateTableData()
  } catch (error) {
    console.error('加载账单列表失败:', error)
    // 使用模拟数据
    allData.value = [
      { id: 1, billNo: 'BL202602010001', houseId: 1, ownerId: 1, feeItemId: 1, periodStart: '2026-02-01', periodEnd: '2026-02-28', amount: 223.75, paidAmount: 223.75, dueDate: '2026-02-15', generateTime: '2026-02-01T00:10:00', status: 2 },
      { id: 2, billNo: 'BL202602010002', houseId: 2, ownerId: 2, feeItemId: 1, periodStart: '2026-02-01', periodEnd: '2026-02-28', amount: 300.00, paidAmount: 0.00, dueDate: '2026-02-15', generateTime: '2026-02-01T00:10:00', status: 0 },
      { id: 3, billNo: 'BL202602010003', houseId: 2, ownerId: 2, feeItemId: 2, periodStart: '2026-02-01', periodEnd: '2026-02-28', amount: 300.00, paidAmount: 150.00, dueDate: '2026-02-15', generateTime: '2026-02-01T00:10:00', status: 1 }
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
  searchForm.billNo = ''
  searchForm.feeItemId = ''
  searchForm.status = ''
  searchForm.ownerId = ''
  updateTableData() 
}

const openDialog = (type, row = null) => {
  dialogType.value = type
  dialogVisible.value = true
  if (formRef.value) formRef.value.resetFields()
  if (type === 'add') {
    Object.assign(form, { id: '', billNo: '', houseId: '', ownerId: '', feeItemId: 1, periodStart: '', periodEnd: '', amount: '', dueDate: '', paidAmount: 0, status: 0 })
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
      await createBill(form)
      ElMessage.success('新增成功')
    } else {
      await updateBill(form.id, form)
      ElMessage.success('修改成功')
    }
    dialogVisible.value = false
    loadData()  // 重新加载数据
  } catch (error) {
    ElMessage.error(dialogType.value === 'add' ? '新增失败' : '修改失败')
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该账单吗？', '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    await deleteBill(id)
    ElMessage.success('删除成功')
    loadData()  // 重新加载数据
  } catch (error) {
    if (error !== 'cancel') ElMessage.error('删除失败')
  }
}

const handleSizeChange = (size) => { 
  pagination.pageSize = size 
  // 保持当前页数据正确
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
.bill-container {
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
  background: linear-gradient(90deg, #722ED1 0%, #409EFF 50%, #67C23A 100%);
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
  color: #722ED1;
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
  background: #722ED1;
  color: white;
}

/* 弹窗样式 */
:deep(.el-dialog) {
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
}

:deep(.el-dialog__header) {
  background: linear-gradient(135deg, #722ED1 0%, #6220b0 100%);
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
  box-shadow: 0 0 0 3px rgba(114, 46, 209, 0.1);
  border-color: #722ED1;
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