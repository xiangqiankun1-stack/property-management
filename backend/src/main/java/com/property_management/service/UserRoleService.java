package com.property_management.service;


import com.property_management.dao.SysUserRole;

import java.util.List;

public interface UserRoleService {
    /**
     * 新增用户角色
     */
    boolean add(SysUserRole sysUserRole);

    /**
     * 更新用户角色
     */
    boolean update(SysUserRole sysUserRole);

    /**
     * 删除用户角色
     */
    boolean delete(Long id);

    /**
     * 获取用户角色列表
     */
    List<String> getUserRole(Long id);
}
