<template>
  <el-container class="layout-container">
    <el-aside width="220px">
      <Aside />
    </el-aside>

    <el-container>
      <el-header>
        <Header />
      </el-header>

      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import Aside from './Aside.vue'
import Header from './Header.vue'
</script>

<style scoped>
.layout-container{
    height:100vh;
}
</style>