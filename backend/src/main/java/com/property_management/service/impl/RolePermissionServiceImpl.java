package com.property_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.property_management.dao.SysPermissionInfo;
import com.property_management.dao.SysRolePermission;
import com.property_management.mapper.PermissionMapper;
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

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<SysPermissionInfo> getRolePermissionList(Long roleId) {
        return rolePermissionMapper.selectByRoleId(roleId);
    }

    @Override
    public List<SysPermissionInfo> getAllPermissions() {
        return permissionMapper.selectList(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignPermissions(Long roleId, List<Long> permissionIds) {
        // 1. ✅ 物理删除该角色的所有权限（直接删除记录）
        rolePermissionMapper.physicalDeleteByRoleId(roleId);

        // 2. 如果没有权限ID，直接返回成功
        if (permissionIds == null || permissionIds.isEmpty()) {
            return true;
        }

        // 3. 批量添加新权限
        for (Long permissionId : permissionIds) {
            SysRolePermission rp = new SysRolePermission();
            rp.setRoleInfoId(roleId);
            rp.setPermissionInfoId(permissionId);
            rolePermissionMapper.insert(rp);
        }

        return true;
    }

    @Override
    public boolean add(SysRolePermission sysRolePermission) {
        // 检查是否已存在（只查未删除的）
        LambdaQueryWrapper<SysRolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRolePermission::getRoleInfoId, sysRolePermission.getRoleInfoId())
                .eq(SysRolePermission::getPermissionInfoId, sysRolePermission.getPermissionInfoId())
                .eq(SysRolePermission::getDeleted, 0);  // ✅ 只查未删除的

        Long count = rolePermissionMapper.selectCount(wrapper);
        if (count > 0) {
            return false;
        }

        return rolePermissionMapper.insert(sysRolePermission) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return rolePermissionMapper.deleteById(id) >= 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRolePermission(Long roleId, Long permissionId) {
        LambdaQueryWrapper<SysRolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRolePermission::getRoleInfoId, roleId)
                .eq(SysRolePermission::getPermissionInfoId, permissionId);
        return rolePermissionMapper.delete(wrapper) > 0;
    }

    @Override
    public boolean update(Long id, SysRolePermission sysRolePermission) {
        sysRolePermission.setId(id);
        return rolePermissionMapper.updateById(sysRolePermission) > 0;
    }
}