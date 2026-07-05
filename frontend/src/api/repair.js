import request from '@/utils/request'

// 查询报修列表
export const getRepairList = (params) => {
  return request({
    url: '/api/v1/repairs',
    method: 'get',
    params
  })
}

// 根据ID查询报修
export const getRepairById = (id) => {
  return request({
    url: `/api/v1/repairs/${id}`,
    method: 'get'
  })
}

// 新增报修
export const createRepair = (data) => {
  return request({
    url: '/api/v1/repairs',
    method: 'post',
    data
  })
}

// 修改报修
export const updateRepair = (id, data) => {
  return request({
    url: `/api/v1/repairs/${id}`,
    method: 'put',
    data
  })
}

// 删除报修
export const deleteRepair = (id) => {
  return request({
    url: `/api/v1/repairs/${id}`,
    method: 'delete'
  })
}

// 报修派单
export const assignRepair = (id, data) => {
  return request({
    url: `/api/v1/repairs/${id}/assign`,
    method: 'put',
    data
  })
}

// 完成报修
export const finishRepair = (id) => {
  return request({
    url: `/api/v1/repairs/${id}/finish`,
    method: 'put'
  })
}

// 报修评价
export const commentRepair = (id, data) => {
  return request({
    url: `/api/v1/repairs/${id}/comment`,
    method: 'post',
    data
  })
}