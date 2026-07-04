<template>
  <el-form :inline="true" :model="model">
    <el-form-item
      v-for="item in fields"
      :key="item.prop"
      :label="item.label"
    >
      <el-input
        v-if="item.type === 'input'"
        v-model="model[item.prop]"
        :placeholder="item.placeholder"
      />

      <el-select
        v-else-if="item.type === 'select'"
        v-model="model[item.prop]"
      >
        <el-option
          v-for="opt in item.options"
          :key="opt.value"
          :label="opt.label"
          :value="opt.value"
        />
      </el-select>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="$emit('search')">查询</el-button>
      <el-button @click="$emit('reset')">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
defineProps({
  model: Object,
  fields: Array
})
</script>