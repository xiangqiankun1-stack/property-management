package com.property_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.property_management.dao.SysPermissionInfo;
import com.property_management.dao.SysRolePermission;
import com.property_management.mapper.RolePermissionMapper;
import com.property_management.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // 检查是否已存在
        LambdaQueryWrapper<SysRolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRolePermission::getRoleInfoId, sysRolePermission.getRoleInfoId())
                .eq(SysRolePermission::getPermissionInfoId, sysRolePermission.getPermissionInfoId());

        Long count = rolePermissionMapper.selectCount(wrapper);
        if (count > 0) {
            return false;
        }

        return rolePermissionMapper.insert(sysRolePermission) > 0;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        return rolePermissionMapper.deleteById(id) >= 0;
    }

    @Override
    public boolean update(Long id, SysRolePermission sysRolePermission) {
        sysRolePermission.setId(id);
        return rolePermissionMapper.updateById(sysRolePermission) > 0;
    }
}