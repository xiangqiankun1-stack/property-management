<template>
  <div>

    <!-- 搜索 -->
    <CrudSearch
      :model="query"
      :fields="searchFields"
      @search="loadData"
      @reset="reset"
    />

    <!-- 新增按钮 -->
    <el-button type="primary" @click="handleAdd" style="margin: 10px 0;">
      新增账单
    </el-button>

    <!-- 表格 -->
    <CrudTable
      :tableData="tableData"
      :columns="columns"
      :loading="loading"
      :total="total"
      :pageSize="query.pageSize"
      :currentPage="query.pageNum"
      @page-change="handlePageChange"
      @edit="handleEdit"
      @delete="handleDelete"
    />

    <!-- 表单 -->
    <CrudForm
      v-model:visible="dialogVisible"
      :model="form"
      :fields="formFields"
      title="账单"
      @submit="submit"
      @cancel="dialogVisible = false"
    />

  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

import {
  getBillList,
  addBill,
  updateBill,
  deleteBill
} from '@/api/bill'

/* ========================
   状态
======================== */
const tableData = ref([])
const loading = ref(false)
const total = ref(0)
const dialogVisible = ref(false)

const mode = ref('add')

/* ========================
   查询条件
======================== */
const query = reactive({
  pageNum: 1,
  pageSize: 10,
  billName: ''
})

/* ========================
   表单
======================== */
const form = reactive({
  id: null,
  billName: '',
  amount: '',
  type: '',
  status: ''
})

/* ========================
   表格配置
======================== */
const columns = [
  { label: '账单名称', prop: 'billName' },
  { label: '金额', prop: 'amount' },
  { label: '类型', prop: 'type' },
  { label: '状态', prop: 'status' },
  { label: '创建时间', prop: 'createTime' }
]

/* ========================
   搜索配置
======================== */
const searchFields = [
  { label: '账单名称', prop: 'billName', type: 'input' }
]

/* ========================
   表单配置
======================== */
const formFields = [
  { label: '账单名称', prop: 'billName' },
  { label: '金额', prop: 'amount' },
  {
    label: '类型',
    prop: 'type',
    type: 'select',
    options: [
      { label: '物业费', value: '物业费' },
      { label: '水费', value: '水费' },
      { label: '电费', value: '电费' }
    ]
  },
  {
    label: '状态',
    prop: 'status',
    type: 'select',
    options: [
      { label: '未缴', value: '未缴' },
      { label: '已缴', value: '已缴' },
      { label: '逾期', value: '逾期' }
    ]
  }
]

/* ========================
   查询
======================== */
const loadData = async () => {
  loading.value = true
  try {
    const res = await getBillList(query)
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
  mode.value = 'add'
  Object.assign(form, {
    id: null,
    billName: '',
    amount: '',
    type: '',
    status: ''
  })
  dialogVisible.value = true
}

/* ========================
   编辑
======================== */
const handleEdit = (row) => {
  mode.value = 'edit'
  Object.assign(form, row)
  dialogVisible.value = true
}

/* ========================
   删除
======================== */
const handleDelete = (row) => {
  ElMessageBox.confirm('确定删除该账单吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    await deleteBill(row.id)
    ElMessage.success('删除成功')
    loadData()
  })
}

/* ========================
   提交
======================== */
const submit = async () => {
  try {
    if (mode.value === 'add') {
      await addBill(form)
      ElMessage.success('新增成功')
    } else {
      await updateBill(form)
      ElMessage.success('修改成功')
    }

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

/* ========================
   重置
======================== */
const reset = () => {
  query.billName = ''
  query.pageNum = 1
  loadData()
}

/* 初始化 */
onMounted(loadData)
</script>