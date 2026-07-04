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
    public boolean delete(Long id) {
        return roleMapper.deleteById(id) > 0;
    }

    @Override
    public boolean update(SysRoleInfo sysRoleInfo) {
        return roleMapper.updateById(sysRoleInfo) > 0;
    }
}
