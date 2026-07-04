import request from '@/utils/request'

// 查询缴费记录
export const getPaymentList = (params) => {
  return request({
    url: '/payments',
    method: 'get',
    params
  })
}

// 新增缴费记录
export const createPayment = (data) => {
  return request({
    url: '/payments',
    method: 'post',
    data
  })
}

// 删除缴费记录
export const deletePayment = (id) => {
  return request({
    url: `/payments/${id}`,
    method: 'delete'
  })
}