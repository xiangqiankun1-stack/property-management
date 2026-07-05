import request from '@/utils/request'

// 查询角色列表
export const getRoleList = (params) => {
  return request({
    url: '/api/v1/roles',
    method: 'get',
    params
  })
}

// 根据ID查询角色
export const getRoleById = (id) => {
  return request({
    url: `/api/v1/roles/${id}`,
    method: 'get'
  })
}

// 新增角色
export const createRole = (data) => {
  return request({
    url: '/api/v1/roles',
    method: 'post',
    data
  })
}

// 修改角色
export const updateRole = (id, data) => {
  return request({
    url: `/api/v1/roles/${id}`,
    method: 'put',
    data
  })
}

// 删除角色
export const deleteRole = (id) => {
  return request({
    url: `/api/v1/roles/${id}`,
    method: 'delete'
  })
}