<template>
  <el-card>
    <el-table :data="list">
      <el-table-column prop="name" label="姓名"/>
      <el-table-column prop="phone" label="电话"/>
      <el-table-column prop="house" label="房屋"/>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'

const list = ref([
  { name: '张三', phone: '138xxxx', house: '1栋101' }
])
</script>