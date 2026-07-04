import request from '@/utils/request'

// 查询缴费记录
export const getPaymentList = (params) => {
  return request({
    url: '/payment/list',
    method: 'get',
    params
  })
}

// 新增缴费记录
export const addPayment = (data) => {
  return request({
    url: '/payment',
    method: 'post',
    data
  })
}
//test
export const deletePayment = (id) => {
  return request({
    url: `/payment/${id}`,
    method: 'delete'
  })
}