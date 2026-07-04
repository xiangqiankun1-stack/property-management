<template>
  <el-card>
    <el-button type="primary">提交报修</el-button>

    <el-table :data="list">
      <el-table-column prop="title" label="问题"/>
      <el-table-column prop="status" label="状态"/>
      <el-table-column prop="time" label="时间"/>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'

const list = ref([
  { title: '水管漏水', status: '处理中', time: '2026-07-04' }
])
</script>