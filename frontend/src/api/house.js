import request from '@/utils/request'

// 查询房屋列表
export const getHouseList = (params) => {
  return request({
    url: '/houses',
    method: 'get',
    params
  })
}

// 根据ID查询房屋
export const getHouseById = (id) => {
  return request({
    url: `/houses/${id}`,
    method: 'get'
  })
}

// 新增房屋
export const createHouse = (data) => {
  return request({
    url: '/houses',
    method: 'post',
    data
  })
}

// 修改房屋
export const updateHouse = (id, data) => {
  return request({
    url: `/houses/${id}`,
    method: 'put',
    data
  })
}

// 删除房屋
export const deleteHouse = (id) => {
  return request({
    url: `/houses/${id}`,
    method: 'delete'
  })
}

// 房屋绑定业主
export const bindOwner = (houseId, data) => {
  return request({
    url: `/houses/${houseId}/owner`,
    method: 'put',
    data
  })
}