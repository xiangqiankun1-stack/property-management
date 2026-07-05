import { defineStore } from 'pinia'

export const useMenuStore = defineStore('menu', {
  state: () => ({
    menus: [
      {
        title: '系统管理',
        icon: 'Setting',
        children: [
          { path: '/user', name: '用户管理' },
          { path: '/role', name: '角色管理' }
        ]
      },
      {
        title: '基础数据',
        icon: 'Menu',
        children: [
          { path: '/community', name: '小区管理' },
          { path: '/building', name: '楼栋管理' },
          { path: '/house', name: '房屋管理' },
          { path: '/owner', name: '业主管理' }
        ]
      }
    ]
  })
})