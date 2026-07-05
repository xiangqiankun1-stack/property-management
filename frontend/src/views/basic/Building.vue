<template>
  <el-card>
    <el-button type="primary">新增楼栋</el-button>

    <el-table :data="list" style="margin-top:20px">
      <el-table-column prop="community" label="所属小区"/>
      <el-table-column prop="name" label="楼栋名称"/>
      <el-table-column prop="floor" label="楼层数"/>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'

const list = ref([
  { community: '阳光小区', name: '1栋', floor: 18 }
])
</script>