import request from '@/utils/request'

// 首页统计
export const getDashboardSummary = () => {
  return request({
    url: '/statistics/home',
    method: 'get'
  })
}

// 报修趋势
export const getRepairTrend = () => {
  return request({
    url: '/statistics/repair',
    method: 'get'
  })
}

// 投诉趋势
export const getComplaintTrend = () => {
  return request({
    url: '/statistics/complaint',
    method: 'get'
  })
}

// 缴费分布
export const getPaymentDistribution = () => {
  return request({
    url: '/statistics/payment',
    method: 'get'
  })
}