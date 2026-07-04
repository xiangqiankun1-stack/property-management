package com.property_management.service;

import com.property_management.dao.Community;

import java.util.List;

public interface CommunityService {

    /**
     * 查询所有小区
     */
    List<Community> getAll();

    /**
     * 根据ID查询小区
     */
    Community getById(Long id);

    /**
     * 新增小区
     */
    boolean add(Community community);

    /**
     * 更新小区
     */
    boolean update(Community community);

    /**
     * 删除小区
     */
    boolean delete(Long id);
}
