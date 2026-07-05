package com.property_management.service;

import com.property_management.dao.RepairOrder;

import java.util.List;

public interface RepairOrderService {

    /**
     * 查询所有派单记录
     */
    List<RepairOrder> getAll();

    /**
     * 根据ID查询派单记录
     */
    RepairOrder getById(Long id);

    /**
     * 新增派单记录
     */
    boolean add(RepairOrder repairOrder);

    /**
     * 更新派单记录
     */
    boolean update(RepairOrder repairOrder);

    /**
     * 删除派单记录
     */
    boolean delete(Long id);
}
