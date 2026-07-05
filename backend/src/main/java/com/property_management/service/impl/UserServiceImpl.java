package com.property_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.property_management.dao.SysUserInfo;
import com.property_management.dao.SysUserRole;
import com.property_management.mapper.UserMapper;
import com.property_management.mapper.UserRoleMapper;
import com.property_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public SysUserInfo findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public boolean register(SysUserInfo sysUserInfo) {
        return userMapper.insert(sysUserInfo) > 0;
    }

    @Override
    public List<SysUserInfo> getAll() {
        return userMapper.selectList(null);
    }

    @Override
    public SysUserInfo getById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public boolean add(SysUserInfo sysUserInfo) {
        return userMapper.insert(sysUserInfo) > 0;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        // 1. 先删除用户角色关联
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserInfoId, id);
        userRoleMapper.delete(wrapper);

        // 2. 再删除用户（逻辑删除）
        return userMapper.deleteById(id) > 0;
    }

    @Override
    public boolean update(SysUserInfo sysUserInfo) {
        return userMapper.updateById(sysUserInfo) > 0;
    }
}