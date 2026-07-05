import request from '@/utils/request'

// 查询楼栋列表
export const getBuildingList = (params) => {
  return request({
    url: '/api/v1/buildings',
    method: 'get',
    params
  })
}

// 根据ID查询楼栋
export const getBuildingById = (id) => {
  return request({
    url: `/api/v1/buildings/${id}`,
    method: 'get'
  })
}

// 新增楼栋
export const createBuilding = (data) => {
  return request({
    url: '/api/v1/buildings',
    method: 'post',
    data
  })
}

// 修改楼栋
export const updateBuilding = (id, data) => {
  return request({
    url: `/api/v1/buildings/${id}`,
    method: 'put',
    data
  })
}

// 删除楼栋
export const deleteBuilding = (id) => {
  return request({
    url: `/api/v1/buildings/${id}`,
    method: 'delete'
  })
}