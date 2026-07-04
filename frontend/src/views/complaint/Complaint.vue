<template>
  <el-card>
    <el-table :data="list">
      <el-table-column prop="content" label="投诉内容"/>
      <el-table-column prop="status" label="状态"/>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'

const list = ref([
  { content: '噪音问题', status: '已处理' }
])
</script>