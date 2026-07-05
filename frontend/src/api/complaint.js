// src/api/complaint.js
import request from '@/utils/request'

// 获取投诉列表 - 改为单数 complaint
export const getComplaintList = (params) => {
  return request({
    url: '/api/v1/complaint',  // ← 改为单数
    method: 'get',
    params
  })
}

// 获取投诉详情 - 改为单数
export const getComplaintById = (id) => {
  return request({
    url: `/api/v1/complaint/${id}`,  // ← 改为单数
    method: 'get'
  })
}

// 创建投诉 - 改为单数
export const createComplaint = (data) => {
  return request({
    url: '/api/v1/complaint',  // ← 改为单数
    method: 'post',
    data
  })
}

// 更新投诉 - 改为单数
export const updateComplaint = (id, data) => {
  return request({
    url: `/api/v1/complaint/${id}`,  // ← 改为单数
    method: 'put',
    data
  })
}

// 删除投诉 - 改为单数
export const deleteComplaint = (id) => {
  return request({
    url: `/api/v1/complaint/${id}`,  // ← 改为单数
    method: 'delete'
  })
}

// 处理投诉 - 改为单数，并使用 /handle 路径
export const handleComplaint = (id, data) => {
  return request({
    url: `/api/v1/complaint/${id}/handle`,  // ← 改为单数 + /handle
    method: 'put',
    data
  })
}