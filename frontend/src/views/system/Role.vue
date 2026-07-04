<template>
  <el-card>
    <el-button type="primary">新增角色</el-button>

    <el-table :data="list" style="margin-top:20px">
      <el-table-column prop="id" label="ID"/>
      <el-table-column prop="name" label="角色名称"/>
      <el-table-column label="操作">
        <template #default>
          <el-button size="small">权限</el-button>
          <el-button size="small" type="danger">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

  </el-card>
</template>

<script setup>
import { ref } from 'vue'

const list = ref([
  { id: 1, name: '管理员' },
  { id: 2, name: '普通用户' }
])
</script>