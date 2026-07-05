package com.property_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.property_management.dao.SysRoleInfo;
import com.property_management.dao.SysRolePermission;
import com.property_management.dao.SysUserRole;
import com.property_management.mapper.RoleMapper;
import com.property_management.mapper.RolePermissionMapper;
import com.property_management.mapper.UserRoleMapper;
import com.property_management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<SysRoleInfo> getAll() {
        return roleMapper.selectList(null);
    }

    @Override
    public SysRoleInfo getById(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public boolean add(SysRoleInfo sysRoleInfo) {
        return roleMapper.insert(sysRoleInfo) > 0;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        // 1. 删除用户角色关联
        LambdaQueryWrapper<SysUserRole> userRoleWrapper = new LambdaQueryWrapper<>();
        userRoleWrapper.eq(SysUserRole::getRoleInfoId, id);
        userRoleMapper.delete(userRoleWrapper);

        // 2. 删除角色权限关联
        LambdaQueryWrapper<SysRolePermission> rolePermWrapper = new LambdaQueryWrapper<>();
        rolePermWrapper.eq(SysRolePermission::getRoleInfoId, id);
        rolePermissionMapper.delete(rolePermWrapper);

        // 3. 删除角色（逻辑删除）
        return roleMapper.deleteById(id) > 0;
    }

    @Override
    public boolean update(SysRoleInfo sysRoleInfo) {
        return roleMapper.updateById(sysRoleInfo) > 0;
    }
}