<template>
  <el-menu
    :default-active="$route.path"
    router
    background-color="#304156"
    text-color="#ffffff"
    active-text-color="#409EFF"
    style="height:100vh"
  >

    <!-- 首页 -->
    <el-menu-item index="/dashboard">
      首页
    </el-menu-item>

    <!-- 动态菜单 -->
    <template v-for="menu in menus" :key="menu.title">

      <el-sub-menu>
        <template #title>
          {{ menu.title }}
        </template>

        <el-menu-item
          v-for="item in menu.children"
          :key="item.path"
          :index="item.path"
        >
          {{ item.name }}
        </el-menu-item>

      </el-sub-menu>

    </template>

  </el-menu>
</template>

<script setup>
import { useMenuStore } from '@/stores/menu'

const menuStore = useMenuStore()
const menus = menuStore.menus
</script>