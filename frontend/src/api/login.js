// src/api/login.js - 改回原来的
import request from '@/utils/request'

export const login = (data) => {
  const params = new URLSearchParams()
  params.append('username', data.username)
  params.append('password', data.password)
  
  return request({
    url: '/api/v1/users/login',  // ✅ 改回 /api/v1/users/login
    method: 'post',
    data: params
  })
}

export const register = (data) => {
  return request({
    url: '/api/v1/users/register',  // ✅ 改回 /api/v1/users/register
    method: 'post',
    data
  })
}