package com.property_management.service;


import com.property_management.dao.SysRoleInfo;

import java.util.List;

public interface RoleService {
    List<SysRoleInfo> getAlls();

    void addRole(SysRoleInfo sysRoleInfo);

    void deleteRole(SysRoleInfo sysRoleInfo);

    void updateRole(SysRoleInfo sysRoleInfo);
}
