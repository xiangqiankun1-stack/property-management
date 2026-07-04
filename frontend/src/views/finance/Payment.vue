<template>
  <div>

    <!-- 搜索 -->
    <CrudSearch
      :model="query"
      :fields="searchFields"
      @search="loadData"
      @reset="reset"
    />

    <!-- 表格 -->
    <CrudTable
      :tableData="tableData"
      :columns="columns"
      :loading="loading"
      :total="total"
      :pageSize="query.pageSize"
      :currentPage="query.pageNum"
      @page-change="handlePageChange"
      @delete="handleDelete"
    />

    <!-- 表单 -->
    <CrudForm
      v-model:visible="dialogVisible"
      :model="form"
      :fields="formFields"
      title="缴费"
      @submit="submit"
      @cancel="dialogVisible = false"
    />

  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

import {
  getPaymentList,
  addPayment,
  deletePayment
} from '@/api/payment'

/* ========================
   状态
======================== */
const tableData = ref([])
const loading = ref(false)
const total = ref(0)
const dialogVisible = ref(false)

/* ========================
   查询条件
======================== */
const query = reactive({
  pageNum: 1,
  pageSize: 10,
  payer: ''
})

/* ========================
   表单
======================== */
const form = reactive({
  billId: '',
  payer: '',
  amount: '',
  method: '',
  status: '成功'
})

/* ========================
   表格配置
======================== */
const columns = [
  { label: '缴费人', prop: 'payer' },
  { label: '金额', prop: 'amount' },
  { label: '支付方式', prop: 'method' },
  { label: '状态', prop: 'status' },
  { label: '时间', prop: 'payTime' }
]

/* ========================
   搜索
======================== */
const searchFields = [
  { label: '缴费人', prop: 'payer', type: 'input' }
]

/* ========================
   表单字段（重点🔥）
======================== */
const formFields = [
  {
    label: '账单ID',
    prop: 'billId'
  },
  {
    label: '缴费人',
    prop: 'payer'
  },
  {
    label: '金额',
    prop: 'amount'
  },
  {
    label: '支付方式',
    prop: 'method',
    type: 'select',
    options: [
      { label: '微信', value: '微信' },
      { label: '支付宝', value: '支付宝' },
      { label: '现金', value: '现金' }
    ]
  }
]

/* ========================
   查询列表
======================== */
const loadData = async () => {
  loading.value = true
  try {
    const res = await getPaymentList(query)
    tableData.value = res.records || []
    total.value = res.total || 0
  } finally {
    loading.value = false
  }
}

/* ========================
   新增
======================== */
const handleAdd = () => {
  Object.assign(form, {
    billId: '',
    payer: '',
    amount: '',
    method: '',
    status: '成功'
  })
  dialogVisible.value = true
}

/* ========================
   删除
======================== */
const handleDelete = (row) => {
  ElMessageBox.confirm('确定删除该缴费记录吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    await deletePayment(row.id)
    ElMessage.success('删除成功')
    loadData()
  })
}

/* ========================
   提交
======================== */
const submit = async () => {
  if (!form.payer || !form.amount) {
    ElMessage.error('请填写完整信息')
    return
  }

  try {
    await addPayment(form)
    ElMessage.success('缴费成功')

    dialogVisible.value = false
    loadData()
  } catch (e) {
    console.error(e)
  }
}

/* ========================
   分页
======================== */
const handlePageChange = (page) => {
  query.pageNum = page
  loadData()
}

/* =======================
   重置
======================== */
const reset = () => {
  query.payer = ''
  query.pageNum = 1
  loadData()
}

/* 初始化 */
onMounted(loadData)
</script>