import request from '@/utils/request'

// 查询业主列表
export const getOwnerList = (params) => {
  return request({
    url: '/api/v1/owners',
    method: 'get',
    params
  })
}

// 根据ID查询业主
export const getOwnerById = (id) => {
  return request({
    url: `/api/v1/owners/${id}`,
    method: 'get'
  })
}

// 新增业主
export const createOwner = (data) => {
  return request({
    url: '/api/v1/owners',
    method: 'post',
    data
  })
}

// 修改业主
export const updateOwner = (id, data) => {
  return request({
    url: `/api/v1/owners/${id}`,
    method: 'put',
    data
  })
}

// 删除业主
export const deleteOwner = (id) => {
  return request({
    url: `/api/v1/owners/${id}`,
    method: 'delete'
  })
}