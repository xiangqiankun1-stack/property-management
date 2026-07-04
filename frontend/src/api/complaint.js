import request from '@/utils/request'

// 查询投诉列表
export const getComplaintList = (params) => {
  return request({
    url: '/complaints',
    method: 'get',
    params
  })
}

// 根据ID查询投诉
export const getComplaintById = (id) => {
  return request({
    url: `/complaints/${id}`,
    method: 'get'
  })
}

// 新增投诉
export const createComplaint = (data) => {
  return request({
    url: '/complaints',
    method: 'post',
    data
  })
}

// 修改投诉
export const updateComplaint = (id, data) => {
  return request({
    url: `/complaints/${id}`,
    method: 'put',
    data
  })
}

// 删除投诉
export const deleteComplaint = (id) => {
  return request({
    url: `/complaints/${id}`,
    method: 'delete'
  })
}

// 回复投诉
export const replyComplaint = (id, data) => {
  return request({
    url: `/complaints/${id}/reply`,
    method: 'put',
    data
  })
}