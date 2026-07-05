// src/api/payment.js
import request from '@/utils/request'

// 修改为正确的路径 - 添加 /api/v1 前缀，使用单数
const BASE_URL = '/api/v1/payment'  // ← 添加 /api/v1 前缀

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