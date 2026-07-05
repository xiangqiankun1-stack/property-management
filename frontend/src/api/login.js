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
  // 根据后端实体字段名修改
  const postData = {
    userName: data.username || '',      // 用户名（登录账号）
    password: data.password || '',      // 登录密码
    phoneNumber: data.phone || '',      // 手机号（后端字段名是 phoneNumber）
    fullName: data.realName || '',      // 姓名（后端字段名是 fullName）
    avatarAddress: '',                  // 头像地址（可选）
    status: 1,                          // 用户状态（可选）
    userType: 1                         // 用户类型（可选）
  }
  
  console.log('注册发送数据:', postData)
  
  return request({
    url: '/api/v1/users/register',  // ✅ 改回 /api/v1/users/register
    method: 'post',
    data: postData,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}