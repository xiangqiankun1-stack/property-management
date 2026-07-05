package com.property_management.service;

import com.property_management.dao.Inspection;

import java.util.List;

public interface InspectionService {

    /**
     * 查询所有巡检任务
     */
    List<Inspection> getAll();

    /**
     * 根据ID查询巡检任务
     */
    Inspection getById(Long id);

    /**
     * 新增巡检任务
     */
    boolean add(Inspection inspection);

    /**
     * 更新巡检任务
     */
    boolean update(Inspection inspection);

    /**
     * 删除巡检任务
     */
    boolean delete(Long id);
}
