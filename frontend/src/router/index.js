import {createRouter,createWebHistory} from 'vue-router'

import Layout from '@/layout/Layout.vue'

const routes=[

{
    path:'/',
    redirect:'/dashboard'
},

{
    path:'/',
    component:Layout,
    children:[

        {
            path:'dashboard',
            component:()=>import('@/views/dashboard/index.vue')
        },

        {
            path:'user',
            component:()=>import('@/views/system/User.vue')
        },

        {
            path:'role',
            component:()=>import('@/views/system/Role.vue')
        },

        {
            path:'community',
            component:()=>import('@/views/basic/Community.vue')
        },

        {
            path:'building',
            component:()=>import('@/views/basic/Building.vue')
        },

        {
            path:'house',
            component:()=>import('@/views/basic/House.vue')
        },

        {
            path:'owner',
            component:()=>import('@/views/basic/Owner.vue')
        }

    ]
}

]

export default createRouter({

history:createWebHistory(),

routes

})