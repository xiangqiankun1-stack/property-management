package com.property_management.service;

import com.property_management.dao.SysUserInfo;

import java.util.List;

public interface UserService {
    /**
     * 根据用户名查询用户
     */
    SysUserInfo findByUserName(String username);

    /**
     * 用户注册
     */
    boolean register(SysUserInfo sysUserInfo);

    /**
     * 查询所有用户
     */
    List<SysUserInfo> getAll();

    /**
     * 根据ID查询用户
     */
    SysUserInfo getById(Long id);

    /**
     * 新增用户
     */
    boolean add(SysUserInfo sysUserInfo);

    /**
     * 更新用户
     */
    boolean update(SysUserInfo sysUserInfo);

    /**
     * 删除用户
     */
    boolean delete(Long id);
}
