// src/api/payment.js
import request from '@/utils/request'

// 使用远程分支的路径
const BASE_URL = '/api/v1/payment-records'

export const getPaymentList = (params) => {
  console.log('缴费请求参数:', params)
  return request({
    url: BASE_URL,
    method: 'get',
    params
  })
}

export const getPaymentById = (id) => {
  return request({
    url: `${BASE_URL}/${id}`,
    method: 'get'
  })
}

export const createPayment = (data) => {
  return request({
    url: BASE_URL,
    method: 'post',
    data
  })
}

export const updatePayment = (id, data) => {
  return request({
    url: `${BASE_URL}/${id}`,
    method: 'put',
    data
  })
}

export const deletePayment = (id) => {
  return request({
    url: `${BASE_URL}/${id}`,
    method: 'delete'
  })
}