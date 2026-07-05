import request from '@/utils/request'

// 用户登录
export const login = (data) => {
  // 使用 URLSearchParams 发送表单数据（符合 @RequestParam 要求）
  const params = new URLSearchParams()
  params.append('username', data.username)  // 后端字段名是 username
  params.append('password', data.password)  // 后端字段名是 password
  
  return request({
    url: '/api/v1/users/login',  // 后端接口路径
    method: 'post',
    data: params
  })
}

// 用户注册
export const register = (data) => {
  return request({
    url: '/api/v1/users/register',
    method: 'post',
    data
  })
}