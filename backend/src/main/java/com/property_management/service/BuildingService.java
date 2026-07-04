package com.property_management.service;

import com.property_management.dao.Building;

import java.util.List;

public interface BuildingService {

    /**
     * 查询所有楼栋
     */
    List<Building> getAll();

    /**
     * 根据ID查询楼栋
     */
    Building getById(Long id);

    /**
     * 新增楼栋
     */
    boolean add(Building building);

    /**
     * 更新楼栋
     */
    boolean update(Building building);

    /**
     * 删除楼栋
     */
    boolean delete(Long id);
}
