import request from '@/utils/request'

// 顶部统计卡片
export const getDashboardSummary = () => {
  return request({
    url: '/dashboard/summary',
    method: 'get'
  })
}

// 报修趋势
export const getRepairTrend = () => {
  return request({
    url: '/dashboard/repairTrend',
    method: 'get'
  })
}

// 投诉趋势
export const getComplaintTrend = () => {
  return request({
    url: '/dashboard/complaintTrend',
    method: 'get'
  })
}

// 缴费分布
export const getPaymentDistribution = () => {
  return request({
    url: '/dashboard/paymentDistribution',
    method: 'get'
  })
}