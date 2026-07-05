package com.property_management.service;

import com.property_management.dao.Repair;

import java.util.List;

public interface RepairService {

    /**
     * 查询所有报修记录
     */
    List<Repair> getAll();

    /**
     * 根据ID查询报修记录
     */
    Repair getById(Long id);

    /**
     * 新增报修记录
     */
    boolean add(Repair repair);

    /**
     * 更新报修记录
     */
    boolean update(Repair repair);

    /**
     * 删除报修记录
     */
    boolean delete(Long id);
}
