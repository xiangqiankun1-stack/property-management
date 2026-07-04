<template>
  <el-dialog :title="title" v-model="visible" width="500px">
    <el-form :model="model" label-width="80px">
      <el-form-item
        v-for="item in fields"
        :key="item.prop"
        :label="item.label"
      >
        <el-input v-model="model[item.prop]" />
      </el-form-item>
    </el-form>

    <template #footer>
      <el-button @click="$emit('cancel')">取消</el-button>
      <el-button type="primary" @click="$emit('submit')">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
defineProps({
  visible: Boolean,
  title: String,
  model: Object,
  fields: Array
})
</script>