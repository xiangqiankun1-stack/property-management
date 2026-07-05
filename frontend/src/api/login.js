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
    url: '/api/v1/users/register',
    method: 'post',
    data: postData,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}