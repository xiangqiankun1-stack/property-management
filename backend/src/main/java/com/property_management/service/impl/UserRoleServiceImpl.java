package com.property_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.property_management.dao.SysUserRole;
import com.property_management.mapper.UserRoleMapper;
import com.property_management.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public boolean add(SysUserRole sysUserRole) {

        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserInfoId, sysUserRole.getUserInfoId())
                .eq(SysUserRole::getRoleInfoId, sysUserRole.getRoleInfoId());

        Long count = userRoleMapper.selectCount(wrapper);

        if (count > 0) {
            throw new RuntimeException("该用户已拥有此角色，不能重复添加");
        }

        return userRoleMapper.insert(sysUserRole) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return userRoleMapper.deleteById(id) >= 0;
    }

    @Override
    public boolean update(SysUserRole sysUserRole) {
        return userRoleMapper.updateById(sysUserRole) > 0;
    }

    @Override
    public List<String> getUserRole(Long id) {
        return userRoleMapper.getUserRole(id);
    }
}
