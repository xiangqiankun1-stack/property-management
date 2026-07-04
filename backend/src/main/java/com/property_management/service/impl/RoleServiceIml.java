package com.property_management.service.impl;

import com.property_management.dao.SysRoleInfo;
import com.property_management.mapper.RoleMapper;
import com.property_management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceIml implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<SysRoleInfo> getAlls() {
        return roleMapper.selectList( null);
    }

    @Override
    public void addRole(SysRoleInfo sysRoleInfo) {
        roleMapper.insert(sysRoleInfo);
    }

    @Override
    public void deleteRole(SysRoleInfo sysRoleInfo) {
        roleMapper.deleteById(sysRoleInfo);
    }

    @Override
    public void updateRole(SysRoleInfo sysRoleInfo) {
        roleMapper.updateById(sysRoleInfo);
    }
}
