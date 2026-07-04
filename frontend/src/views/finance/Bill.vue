<template>
  <el-card>
    <el-table :data="list">
      <el-table-column prop="type" label="费用类型"/>
      <el-table-column prop="amount" label="金额"/>
      <el-table-column prop="status" label="状态"/>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'

const list = ref([
  { type: '物业费', amount: 200, status: '未缴' }
])
</script>