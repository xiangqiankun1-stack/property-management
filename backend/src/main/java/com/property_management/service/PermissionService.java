package com.property_management.service;


import com.property_management.dao.SysPermissionInfo;

import java.util.List;

public interface PermissionService {
    List<SysPermissionInfo> getAlls();

    void addPermission(SysPermissionInfo sysPermissionInfo);

    void deletePermission(SysPermissionInfo sysPermissionInfo);

    void updatePermission(SysPermissionInfo sysPermissionInfo);
}
