package com.property_management.service;

import com.property_management.dao.SysPermissionInfo;
import com.property_management.dao.SysRolePermission;

import java.util.List;

public interface RolePermissionService {

    /**
     * 获取角色的权限列表
     */
    List<SysPermissionInfo> getRolePermissionList(Long roleId);

    /**
     * 获取所有权限（用于权限树）
     */
    List<SysPermissionInfo> getAllPermissions();

    /**
     * 分配角色权限（先删除旧权限，再批量添加新权限）
     */
    boolean assignPermissions(Long roleId, List<Long> permissionIds);

    /**
     * 添加单个角色权限
     */
    boolean add(SysRolePermission sysRolePermission);

    /**
     * 删除角色权限（根据ID）
     */
    boolean delete(Long id);

    /**
     * 删除角色的某个权限（根据角色ID和权限ID）
     */
    boolean deleteRolePermission(Long roleId, Long permissionId);

    /**
     * 更新角色权限
     */
    boolean update(Long id, SysRolePermission sysRolePermission);
}