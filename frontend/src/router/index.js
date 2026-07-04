import { createRouter, createWebHistory } from 'vue-router'

import Layout from '@/layout/Layout.vue'

const routes = [
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/Dashboard.vue')
      },

      {
        path: 'user',
        name: 'User',
        component: () => import('@/views/system/User.vue')
      },

      {
        path: 'role',
        name: 'Role',
        component: () => import('@/views/system/Role.vue')
      },

      {
        path: 'community',
        name: 'Community',
        component: () => import('@/views/basic/Community.vue')
      },

      {
        path: 'building',
        name: 'Building',
        component: () => import('@/views/basic/Building.vue')
      },

      {
        path: 'house',
        name: 'House',
        component: () => import('@/views/basic/House.vue')
      },

      {
        path: 'owner',
        name: 'Owner',
        component: () => import('@/views/basic/Owner.vue')
      }
    ]
  }
]

export default createRouter({
  history: createWebHistory(),
  routes
})