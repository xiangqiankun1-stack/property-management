package com.property_management.service;

import com.property_management.dao.SysPermissionInfo;
import com.property_management.dao.SysRolePermission;

import java.util.List;

public interface RolePermissionService {
    List<SysPermissionInfo> getRolePermissionList(Long roleId);

    boolean add(SysRolePermission sysRolePermission);

    boolean delete(Long id);

    boolean update(Long id, SysRolePermission sysRolePermission);
}
