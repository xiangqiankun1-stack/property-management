<template>
  <el-card>
    <el-table :data="list">
      <el-table-column prop="building" label="楼栋"/>
      <el-table-column prop="room" label="房号"/>
      <el-table-column prop="area" label="面积"/>
      <el-table-column prop="status" label="状态"/>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'

const list = ref([
  { building: '1栋', room: '101', area: 89, status: '已入住' }
])
</script>