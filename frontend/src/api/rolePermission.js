// src/api/rolePermission.js
import request from '@/utils/request'

/**
 * 获取角色的权限列表
 */
export const getRolePermissions = (roleId) => {
  return request({
    url: `/api/v1/role-permission/${roleId}/permissions`,
    method: 'get'
  })
}

/**
 * 获取所有权限（用于权限树）
 */
export const getAllPermissions = () => {
  return request({
    url: '/api/v1/role-permission/all',
    method: 'get'
  })
}

/**
 * 分配角色权限（完整替换）
 */
export const assignPermissions = (roleId, permissionIds) => {
  return request({
    url: '/api/v1/role-permission/assign',
    method: 'post',
    data: {
      roleId: roleId,
      permissionIds: permissionIds
    }
  })
}

/**
 * 新增单个角色权限
 */
export const addRolePermission = (data) => {
  return request({
    url: '/api/v1/role-permission',
    method: 'post',
    data
  })
}

/**
 * 删除角色权限（根据ID）
 */
export const deleteRolePermission = (id) => {
  return request({
    url: `/api/v1/role-permission/${id}`,
    method: 'delete'
  })
}

/**
 * 删除角色的某个权限（根据角色ID和权限ID）
 */
export const deleteRolePermissionByRoleAndPermission = (roleId, permissionId) => {
  return request({
    url: `/api/v1/role-permission/role/${roleId}/permission/${permissionId}`,
    method: 'delete'
  })
}

/**
 * 更新角色权限
 */
export const updateRolePermission = (id, data) => {
  return request({
    url: `/api/v1/role-permission/${id}`,
    method: 'put',
    data
  })
}