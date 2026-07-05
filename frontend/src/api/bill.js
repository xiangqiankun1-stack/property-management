import request from '@/utils/request'

// 查询账单列表
export const getBillList = (params) => {
  return request({
    url: '/api/v1/bills',
    method: 'get',
    params
  })
}

// 根据ID查询账单
export const getBillById = (id) => {
  return request({
    url: `/api/v1/bills/${id}`,
    method: 'get'
  })
}

// 新增账单
export const createBill = (data) => {
  return request({
    url: '/api/v1/bills',
    method: 'post',
    data
  })
}

// 修改账单
export const updateBill = (id, data) => {
  return request({
    url: `/api/v1/bills/${id}`,
    method: 'put',
    data
  })
}

// 删除账单
export const deleteBill = (id) => {
  return request({
    url: `/api/v1/bills/${id}`,
    method: 'delete'
  })
}