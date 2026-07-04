import request from '@/utils/request'

// 用户列表
export const getUserList = (params) => {
  return request.get('/user/list', { params })
}

// 新增用户
export const addUser = (data) => {
  return request.post('/user/add', data)
}

// 删除用户
export const deleteUser = (id) => {
  return request.delete(`/user/${id}`)
}