package com.property_management.service;


import com.property_management.dao.SysRoleInfo;

import java.util.List;

public interface RoleService {
    /**
     * 查询所有角色
     */
    List<SysRoleInfo> getAll();

    /**
     * 根据ID查询角色
     */
    SysRoleInfo getById(Long id);

    /**
     * 新增角色
     */
    boolean add(SysRoleInfo sysRoleInfo);

    /**
     * 更新角色
     */
    boolean update(SysRoleInfo sysRoleInfo);

    /**
     * 删除角色
     */
    boolean delete(Long id);
}
