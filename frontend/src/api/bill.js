import request from '@/utils/request'

// 查询账单列表
export const getBillList = (params) => {
  return request({
    url: '/bill/list',
    method: 'get',
    params
  })
}

// 新增账单test
export const addBill = (data) => {
  return request({
    url: '/bill',
    method: 'post',
    data
  })
}

// 修改账单
export const updateBill = (data) => {
  return request({
    url: '/bill',
    method: 'put',
    data
  })
}

// 删除账单
export const deleteBill = (id) => {
  return request({
    url: `/bill/${id}`,
    method: 'delete'
  })
}