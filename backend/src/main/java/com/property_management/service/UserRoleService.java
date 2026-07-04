package com.property_management.service;


import com.property_management.dao.SysUserRole;

import java.util.List;

public interface UserRoleService {
    void addUserRole(SysUserRole sysUserRole);

    void deleteUserRole(SysUserRole sysUserRole);

    void updateUserRole(SysUserRole sysUserRole);

    List<String> getUserRole(Long id);
}
