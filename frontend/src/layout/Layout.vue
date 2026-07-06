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
        :default-active="$route.path"
      >
        <el-menu-item index="/dashboard">
          <template #icon><el-icon>Home</el-icon></template>
          <span>首页</span>
        </el-menu-item>

        <el-sub-menu index="1">
          <template #title>
            <span>系统管理</span>
          </template>
          <el-menu-item index="/user">用户管理</el-menu-item>
          <el-menu-item index="/role">角色管理</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="2">
          <template #title>
            <span>基础数据</span>
          </template>
          <el-menu-item index="/community">小区管理</el-menu-item>
          <el-menu-item index="/building">楼栋管理</el-menu-item>
          <el-menu-item index="/house">房屋管理</el-menu-item>
          <el-menu-item index="/owner">业主管理</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="3">
          <template #title>
            <span>服务管理</span>
          </template>
          <el-menu-item index="/repair">报修管理</el-menu-item>
          <el-menu-item index="/complaint">投诉管理</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="4">
          <template #title>
            <span>财务管理</span>
          </template>
          <el-menu-item index="/bill">账单管理</el-menu-item>
          <el-menu-item index="/payment">缴费管理</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <!-- 右侧内容 -->
    <el-container>
      <el-header class="header">
        <div class="header-left">智慧物业管理平台</div>
        <div class="header-right">
          <el-button 
            type="text" 
            @click="handleLogout"
            class="logout-btn"
          >
            <span>退出登录</span>
          </el-button>
        </div>
      </el-header>

      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage, ElMessageBox } from 'element-plus'  // ✅ 添加 ElMessageBox

const router = useRouter()
const userStore = useUserStore()

// Layout.vue - 退出登录
const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // ✅ 清除所有登录信息
    localStorage.removeItem('token')
    localStorage.removeItem('role')
    localStorage.removeItem('userInfo')
    userStore.logout()  // ✅ 清除 store 中的用户信息
    
    router.push('/login')
    ElMessage.success('已退出')
  }).catch(() => {})
}
</script>

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
  padding: 0 20px;
  font-weight: bold;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
.header-left {
  font-size: 18px;
}
.header-right {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.logout-btn {
  color: #666;
  padding: 8px 16px;
  transition: all 0.2s;
}
.logout-btn:hover {
  color: #409EFF;
  background-color: #f0f5ff;
}
</style>