<template>
  <div>
    <el-table
      :data="tableData"
      v-loading="loading"
      border
      style="width: 100%"
    >
      <el-table-column
        v-for="col in columns"
        :key="col.prop"
        :prop="col.prop"
        :label="col.label"
        :width="col.width"
      />

      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button size="small" type="primary" @click="$emit('edit', row)">
            编辑
          </el-button>

          <el-button size="small" type="danger" @click="$emit('delete', row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 15px; text-align: right;">
      <el-pagination
        background
        layout="total, prev, pager, next"
        :total="total"
        :page-size="pageSize"
        :current-page="currentPage"
        @current-change="$emit('page-change', $event)"
      />
    </div>
  </div>
</template>

<script setup>
defineProps({
  tableData: Array,
  columns: Array,
  loading: Boolean,
  total: Number,
  pageSize: Number,
  currentPage: Number
})
</script>