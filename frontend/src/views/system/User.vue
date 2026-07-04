<template>
  <div>
    <el-card>
      <el-button type="primary" @click="openDialog">新增用户</el-button>

      <el-table :data="tableData" style="margin-top:20px">
        <el-table-column prop="id" label="ID" width="80"/>
        <el-table-column prop="username" label="用户名"/>
        <el-table-column prop="role" label="角色"/>
        <el-table-column label="操作">
          <template #default>
            <el-button size="small">编辑</el-button>
            <el-button size="small" type="danger">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" title="用户">
      <el-form>
        <el-form-item label="用户名">
          <el-input />
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password"/>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script setup>
import { ref } from 'vue'

const dialogVisible = ref(false)

const tableData = ref([
  { id: 1, username: 'admin', role: '管理员' }
])

const openDialog = () => {
  dialogVisible.value = true
}
</script>