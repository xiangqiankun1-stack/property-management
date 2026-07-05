package com.property_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.property_management.dao.SysPermissionInfo;
import com.property_management.dao.SysRolePermission;
import com.property_management.mapper.PermissionMapper;
import com.property_management.mapper.RolePermissionMapper;
import com.property_management.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<SysPermissionInfo> getAll() {
        return permissionMapper.selectList(null);
    }

    @Override
    public SysPermissionInfo getById(Long id) {
        return permissionMapper.selectById(id);
    }

    @Override
    public boolean add(SysPermissionInfo sysPermissionInfo) {
        return permissionMapper.insert(sysPermissionInfo) > 0;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        // 1. 删除角色权限关联
        LambdaQueryWrapper<SysRolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRolePermission::getPermissionInfoId, id);
        rolePermissionMapper.delete(wrapper);

        // 2. 删除权限（逻辑删除）
        return permissionMapper.deleteById(id) > 0;
    }

    @Override
    public boolean update(SysPermissionInfo sysPermissionInfo) {
        return permissionMapper.updateById(sysPermissionInfo) > 0;
    }
}