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
    public boolean delete(Long id) {
        return permissionMapper.deleteById(id) > 0;
    }

    @Override
    public boolean update(SysPermissionInfo sysPermissionInfo) {
        return permissionMapper.updateById(sysPermissionInfo) > 0;
    }
}
