<template>
  <el-card>
    <el-button type="primary">新增小区</el-button>

    <el-table :data="list" style="margin-top:20px">
      <el-table-column prop="name" label="小区名称"/>
      <el-table-column prop="address" label="地址"/>
      <el-table-column label="操作">
        <template #default>
          <el-button size="small">编辑</el-button>
          <el-button size="small" type="danger">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

  </el-card>
</template>

<script setup>
import { ref } from 'vue'

const list = ref([
  { name: '阳光小区', address: '1号路' }
])
</script>