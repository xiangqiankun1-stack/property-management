import request from '@/utils/request'

// 用户登录
export const login = (data) => {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

// 获取当前登录用户信息
export const getUserInfo = () => {
  return request({
    url: '/auth/info',
    method: 'get'
  })
}