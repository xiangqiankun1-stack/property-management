package com.property_management.service.impl;

import com.property_management.dao.SysPermissionInfo;
import com.property_management.dao.SysRolePermission;
import com.property_management.mapper.RolePermissionMapper;
import com.property_management.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<SysPermissionInfo> getRolePermissionList(Long roleId) {
        return rolePermissionMapper.selectByRoleId(roleId);
    }

    @Override
    public boolean add(SysRolePermission sysRolePermission) {
        return rolePermissionMapper.insert(sysRolePermission) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return rolePermissionMapper.deleteById(id) >= 0;
    }

    @Override
    public boolean update(Long id, SysRolePermission sysRolePermission) {
        return rolePermissionMapper.updateById(sysRolePermission) > 0;
    }
}
