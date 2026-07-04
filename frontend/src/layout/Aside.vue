<template>
  <el-menu
      default-active="/dashboard"
      router
      background-color="#304156"
      text-color="#ffffff"
      active-text-color="#409EFF"
      style="height:100vh"
  >

    <el-menu-item index="/dashboard">
      首页
    </el-menu-item>

    <el-sub-menu index="1">
      <template #title>
        系统管理
      </template>

      <el-menu-item index="/user">
        用户管理
      </el-menu-item>

      <el-menu-item index="/role">
        角色管理
      </el-menu-item>

    </el-sub-menu>

    <el-sub-menu index="2">
      <template #title>
        基础数据
      </template>

      <el-menu-item index="/community">
        小区管理
      </el-menu-item>

      <el-menu-item index="/building">
        楼栋管理
      </el-menu-item>

      <el-menu-item index="/house">
        房屋管理
      </el-menu-item>

      <el-menu-item index="/owner">
        业主管理
      </el-menu-item>

    </el-sub-menu>

  </el-menu>
</template>