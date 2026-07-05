import request from '@/utils/request'

// 查询小区列表
export const getCommunityList = (params) => {
  return request({
    url: '/api/v1/communities',
    method: 'get',
    params
  })
}

// 根据ID查询小区
export const getCommunityById = (id) => {
  return request({
    url: `/api/v1/communities/${id}`,
    method: 'get'
  })
}

// 新增小区
export const createCommunity = (data) => {
  return request({
    url: '/api/v1/communities',
    method: 'post',
    data
  })
}

// 修改小区
export const updateCommunity = (id, data) => {
  return request({
    url: `/api/v1/communities/${id}`,
    method: 'put',
    data
  })
}

// 删除小区
export const deleteCommunity = (id) => {
  return request({
    url: `/api/v1/communities/${id}`,
    method: 'delete'
  })
}