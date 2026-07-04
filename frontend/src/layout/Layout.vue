<template>
  <el-container style="height: 100vh">
    <!-- 左侧菜单 -->
    <el-aside width="220px" class="aside">
      <div class="logo">智慧物业系统</div>

      <el-menu
        background-color="#001529"
        text-color="#fff"
        active-text-color="#409EFF"
        router
      >
        <el-menu-item index="/dashboard">首页</el-menu-item>

        <el-sub-menu index="1">
          <template #title>系统管理</template>
          <el-menu-item index="/user">用户管理</el-menu-item>
          <el-menu-item index="/role">角色管理</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="2">
          <template #title>基础数据</template>
          <el-menu-item index="/community">小区管理</el-menu-item>
          <el-menu-item index="/building">楼栋管理</el-menu-item>
          <el-menu-item index="/house">房屋管理</el-menu-item>
          <el-menu-item index="/owner">业主管理</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <!-- 右侧内容 -->
    <el-container>
      <el-header class="header">
        智慧物业管理平台
      </el-header>

      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.aside {
  background: #001529;
}
.logo {
  color: #fff;
  padding: 20px;
  font-size: 18px;
  text-align: center;
}
.header {
  background: #fff;
  line-height: 60px;
  padding-left: 20px;
  font-weight: bold;
}
</style>