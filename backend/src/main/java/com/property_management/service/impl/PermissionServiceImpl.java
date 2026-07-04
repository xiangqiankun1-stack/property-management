package com.property_management.service.impl;

import com.property_management.dao.SysPermissionInfo;
import com.property_management.mapper.PermissionMapper;
import com.property_management.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<SysPermissionInfo> getAlls() {
        return permissionMapper.selectList(null);
    }

    @Override
    public void addPermission(SysPermissionInfo sysPermissionInfo) {
        permissionMapper.insert(sysPermissionInfo);
    }

    @Override
    public void deletePermission(SysPermissionInfo sysPermissionInfo) {
        permissionMapper.deleteById(sysPermissionInfo);
    }

    @Override
    public void updatePermission(SysPermissionInfo sysPermissionInfo) {
        permissionMapper.updateById(sysPermissionInfo);
    }
}
