import request from '@/utils/request'

// 查询用户列表
export const getUserList = (params) => {
  return request({
    url: '/users',
    method: 'get',
    params
  })
}

// 根据ID查询用户
export const getUserById = (id) => {
  return request({
    url: `/users/${id}`,
    method: 'get'
  })
}

// 新增用户
export const createUser = (data) => {
  return request({
    url: '/users',
    method: 'post',
    data
  })
}

// 修改用户
export const updateUser = (id, data) => {
  return request({
    url: `/users/${id}`,
    method: 'put',
    data
  })
}

// 删除用户
export const deleteUser = (id) => {
  return request({
    url: `/users/${id}`,
    method: 'delete'
  })
}

// 用户分配角色
export const assignRole = (userId, data) => {
  return request({
    url: `/users/${userId}/role`,
    method: 'put',
    data
  })
}