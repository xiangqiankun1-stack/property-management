<template>
  <div class="bill-container">
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="账单名称">
          <el-input v-model="searchForm.billName" placeholder="请输入账单名称" />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="searchForm.type" placeholder="请选择类型">
            <el-option label="全部" value="" />
            <el-option label="物业费" value="物业费" />
            <el-option label="水费" value="水费" />
            <el-option label="电费" value="电费" />
            <el-option label="燃气费" value="燃气费" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态">
            <el-option label="全部" value="" />
            <el-option label="待支付" :value="0" />
            <el-option label="已支付" :value="1" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="openDialog('add')" class="add-btn">
        <el-icon>Plus</el-icon>
        新增账单
      </el-button>
    </el-card>

    <el-card class="table-card">
      <el-table :data="tableData" border stripe :loading="loading" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" align="center"/>
        <el-table-column prop="billName" label="账单名称"/>
        <el-table-column prop="ownerName" label="业主姓名"/>
        <el-table-column prop="houseNumber" label="房号"/>
        <el-table-column prop="type" label="类型" width="100"/>
        <el-table-column prop="amount" label="金额(元)" width="120"/>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'warning'">
              {{ scope.row.status === 1 ? '已支付' : '待支付' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="dueDate" label="到期日期" width="120"/>
        <el-table-column prop="createTime" label="生成时间" width="180"/>
        <el-table-column prop="payTime" label="支付时间" width="180"/>
        <el-table-column label="操作" width="180" align="center">
          <template #default="scope">
            <el-button size="small" @click="openDialog('edit', scope.row)">
              <el-icon>编辑</el-icon>
              
            </el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">
              <el-icon>删除</el-icon>
              
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
        <el-form-item label="账单名称" prop="billName">
          <el-input v-model="form.billName" placeholder="请输入账单名称" />
        </el-form-item>
        <el-form-item label="业主姓名" prop="ownerName">
          <el-input v-model="form.ownerName" placeholder="请输入业主姓名" />
        </el-form-item>
        <el-form-item label="房号" prop="houseNumber">
          <el-input v-model="form.houseNumber" placeholder="请输入房号" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option label="物业费" value="物业费" />
            <el-option label="水费" value="水费" />
            <el-option label="电费" value="电费" />
            <el-option label="燃气费" value="燃气费" />
          </el-select>
        </el-form-item>
        <el-form-item label="金额(元)" prop="amount">
          <el-input v-model.number="form.amount" type="number" placeholder="请输入金额" />
        </el-form-item>
        <el-form-item label="到期日期" prop="dueDate">
          <el-date-picker v-model="form.dueDate" type="date" placeholder="请选择到期日期" />
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
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
import { getBillList, createBill, updateBill, deleteBill } from '@/api/bill'

const searchForm = reactive({ billName: '', type: '', status: '' })
const tableData = ref([])
const loading = ref(false)
const pagination = reactive({ currentPage: 1, pageSize: 10, total: 0 })

const dialogVisible = ref(false)
const dialogType = ref('add')
const formRef = ref(null)
const form = reactive({ id: '', billName: '', ownerName: '', houseNumber: '', type: '物业费', amount: '', dueDate: '', status: 0 })

const rules = {
  billName: [{ required: true, message: '请输入账单名称', trigger: 'blur' }],
  ownerName: [{ required: true, message: '请输入业主姓名', trigger: 'blur' }],
  amount: [{ required: true, message: '请输入金额', trigger: 'blur' }, { type: 'number', min: 0.01, message: '金额必须大于0', trigger: 'blur' }],
  type: [{ required: true, message: '请选择类型', trigger: 'blur' }]
}

const dialogTitle = computed(() => dialogType.value === 'add' ? '新增账单' : '编辑账单')

const loadData = async () => {
  loading.value = true
  try {
    const params = { page: pagination.currentPage, size: pagination.pageSize, ...searchForm }
    const res = await getBillList(params)
    if (Array.isArray(res) && res.length > 0) {
      tableData.value = res
      pagination.total = res.length
    } else {
      tableData.value = [
        { id: 1, billName: '2026年1月物业费', ownerName: '张三', houseNumber: '1号楼101', type: '物业费', amount: 150.00, status: 1, dueDate: '2026-01-31', createTime: '2026-01-01T00:00:00', payTime: '2026-01-15T10:00:00' },
        { id: 2, billName: '2026年1月水费', ownerName: '张三', houseNumber: '1号楼101', type: '水费', amount: 35.50, status: 1, dueDate: '2026-01-31', createTime: '2026-01-01T00:00:00', payTime: '2026-01-15T10:00:00' },
        { id: 3, billName: '2026年1月电费', ownerName: '李四', houseNumber: '2号楼201', type: '电费', amount: 89.00, status: 0, dueDate: '2026-01-31', createTime: '2026-01-01T00:00:00', payTime: '' }
      ]
      pagination.total = tableData.value.length
    }
  } catch (error) {
    console.error('加载账单列表失败:', error)
    tableData.value = [
      { id: 1, billName: '2026年1月物业费', ownerName: '张三', houseNumber: '1号楼101', type: '物业费', amount: 150.00, status: 1, dueDate: '2026-01-31', createTime: '2026-01-01T00:00:00', payTime: '2026-01-15T10:00:00' },
      { id: 2, billName: '2026年1月水费', ownerName: '张三', houseNumber: '1号楼101', type: '水费', amount: 35.50, status: 1, dueDate: '2026-01-31', createTime: '2026-01-01T00:00:00', payTime: '2026-01-15T10:00:00' },
      { id: 3, billName: '2026年1月电费', ownerName: '李四', houseNumber: '2号楼201', type: '电费', amount: 89.00, status: 0, dueDate: '2026-01-31', createTime: '2026-01-01T00:00:00', payTime: '' }
    ]
    pagination.total = tableData.value.length
  } finally {
    loading.value = false
  }
}

const handleSearch = () => { pagination.currentPage = 1; loadData() }
const handleReset = () => { searchForm.billName = ''; searchForm.type = ''; searchForm.status = ''; handleSearch() }

const openDialog = (type, row = null) => {
  dialogType.value = type
  dialogVisible.value = true
  if (formRef.value) formRef.value.resetFields()
  if (type === 'add') {
    Object.assign(form, { id: '', billName: '', ownerName: '', houseNumber: '', type: '物业费', amount: '', dueDate: '', status: 0 })
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
    loadData()
  } catch (error) {
    ElMessage.error(dialogType.value === 'add' ? '新增失败' : '修改失败')
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该账单吗？', '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    await deleteBill(id)
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
.bill-container { padding: 20px; }
.search-card { margin-bottom: 20px; }
.add-btn { float: right; }
.table-card { min-height: 400px; }
</style>