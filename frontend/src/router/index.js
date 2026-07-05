import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/layout/Layout.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/Login.vue')
  },
    {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/login/register.vue')
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', name: 'Dashboard', component: () => import('@/views/dashboard/Dashboard.vue') },
      { path: 'user', name: 'User', component: () => import('@/views/system/User.vue') },
      { path: 'role', name: 'Role', component: () => import('@/views/system/Role.vue') },
      { path: 'community', name: 'Community', component: () => import('@/views/basic/Community.vue') },
      { path: 'building', name: 'Building', component: () => import('@/views/basic/Building.vue') },
      { path: 'house', name: 'House', component: () => import('@/views/basic/House.vue') },
      { path: 'owner', name: 'Owner', component: () => import('@/views/basic/Owner.vue') },
      { path: 'repair', name: 'Repair', component: () => import('@/views/repair/Repair.vue') },
      { path: 'complaint', name: 'Complaint', component: () => import('@/views/complaint/Complaint.vue') },
      { path: 'bill', name: 'Bill', component: () => import('@/views/finance/Bill.vue') },
      { path: 'payment', name: 'Payment', component: () => import('@/views/finance/Payment.vue') }
    ]
  }
]



const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to) => {
  const token = localStorage.getItem('token')
  
  // 未登录且访问非登录/注册页，跳转登录
  if (to.path !== '/login' && to.path !== '/register' && !token) {
    return '/login'
  } 
  // 已登录访问登录/注册页，跳转首页
  if ((to.path === '/login' || to.path === '/register') && token) {
    return '/dashboard'
  }
})

export default router