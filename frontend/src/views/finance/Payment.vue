<template>
  <el-card>
    <el-table :data="list">
      <el-table-column prop="bill" label="账单"/>
      <el-table-column prop="money" label="金额"/>
      <el-table-column prop="time" label="缴费时间"/>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'

const list = ref([
  { bill: '物业费', money: 200, time: '2026-07-01' }
])
</script>