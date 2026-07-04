package com.property_management.service.impl;

import com.property_management.dao.SysUserInfo;
import com.property_management.mapper.UserMapper;
import com.property_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public SysUserInfo findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void register(SysUserInfo sysUserInfo) {
        userMapper.insert(sysUserInfo);
    }

    @Override
    public List<SysUserInfo> getAlls() {
        return userMapper.selectList(null);
    }
}
