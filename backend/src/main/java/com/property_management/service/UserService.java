package com.property_management.service;

import com.property_management.dao.SysUserInfo;

import java.util.List;

public interface UserService {
    SysUserInfo findByUserName(String username);

    void register(SysUserInfo sysUserInfo);

    List<SysUserInfo> getAlls();
}
