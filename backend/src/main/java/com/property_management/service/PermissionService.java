package com.property_management.service;


import com.property_management.dao.SysPermissionInfo;

import java.util.List;

public interface PermissionService {
    /**
     * 查询所有权限
     */
    List<SysPermissionInfo> getAll();

    /**
     * 根据ID查询权限
     */
    SysPermissionInfo getById(Long id);

    /**
     * 新增权限
     */
    boolean add(SysPermissionInfo sysPermissionInfo);

    /**
     * 更新权限
     */
    boolean update(SysPermissionInfo sysPermissionInfo);

    /**
     * 删除权限
     */
    boolean delete(Long id);
}
