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
      新增报修
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
      title="报修"
      @submit="submit"
      @cancel="dialogVisible = false"
    />

  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

import {
  getRepairList,
  addRepair,
  updateRepair,
  deleteRepair
} from '@/api/repair'

/* ========================
   数据
======================== */
const tableData = ref([])
const loading = ref(false)
const total = ref(0)
const dialogVisible = ref(false)

/* 当前模式：add / edit */
const mode = ref('add')

/* ========================
   查询条件
======================== */
const query = reactive({
  pageNum: 1,
  pageSize: 10,
  title: ''
})

/* ========================
   表单数据
======================== */
const form = reactive({
  id: null,
  title: '',
  description: '',
  status: ''
})

/* ========================
   表格配置
======================== */
const columns = [
  { label: '标题', prop: 'title' },
  { label: '状态', prop: 'status' },
  { label: '创建时间', prop: 'createTime' }
]

const searchFields = [
  { label: '标题', prop: 'title', type: 'input' }
]

const formFields = [
  { label: '标题', prop: 'title' },
  { label: '描述', prop: 'description' }
]

/* ========================
   查询列表
======================== */
const loadData = async () => {
  loading.value = true
  try {
    const res = await getRepairList(query)
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
    title: '',
    description: '',
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
  ElMessageBox.confirm('确定删除该报修记录吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    await deleteRepair(row.id)
    ElMessage.success('删除成功')
    loadData()
  })
}

/* ========================
   提交（新增/修改合并）
======================== */
const submit = async () => {
  if (!form.title) {
    ElMessage.error('标题不能为空')
    return
  }

  try {
    if (mode.value === 'add') {
      await addRepair(form)
      ElMessage.success('新增成功')
    } else {
      await updateRepair(form)
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
======================= */
const reset = () => {
  query.title = ''
  query.pageNum = 1
  loadData()
}

/* 初始化 */
onMounted(loadData)
</script>