<template>
  <el-card>

    <CrudTable
      :tableData="tableData"
      :columns="columns"
      :loading="loading"
    />

  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getComplaintList } from '@/api/complaint'

/* ===== 数据 ===== */
const tableData = ref([])
const loading = ref(false)

/* ===== 表格配置 ===== */
const columns = [
  { label: '投诉内容', prop: 'content' },
  { label: '状态', prop: 'status' }
]

/* ===== 请求数据 ===== */
const loadData = async () => {
  loading.value = true
  try {
    const res = await getComplaintList({ pageNum: 1, pageSize: 10 })
    tableData.value = res.records || []
  } catch (e) {
    console.log('接口未联通，使用mock')
    tableData.value = [
      { content: '噪音问题', status: '已处理' }
    ]
  } finally {
    loading.value = false
  }
}

/* ===== 初始化 ===== */
onMounted(loadData)
</script>