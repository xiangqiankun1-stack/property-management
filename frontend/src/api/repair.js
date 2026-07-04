import request from '@/utils/request'

export const getRepairList = (params) => {
  return request({
    url: '/repair/list',
    method: 'get',
    params
  })
}

export const addRepair = (data) => {
  return request({
    url: '/repair',
    method: 'post',
    data
  })
}

export const updateRepair = (data) => {
  return request({
    url: '/repair',
    method: 'put',
    data
  })
}

//test
export const deleteRepair = (id) => {
  return request({
    url: `/repair/${id}`,
    method: 'delete'
  })
}