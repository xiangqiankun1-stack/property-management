package com.property_management.service;

import com.property_management.dao.InspectionRecord;

import java.util.List;

public interface InspectionRecordService {

    /**
     * 查询所有巡检整改记录
     */
    List<InspectionRecord> getAll();

    /**
     * 根据ID查询巡检整改记录
     */
    InspectionRecord getById(Long id);

    /**
     * 新增巡检整改记录
     */
    boolean add(InspectionRecord inspectionRecord);

    /**
     * 更新巡检整改记录
     */
    boolean update(InspectionRecord inspectionRecord);

    /**
     * 删除巡检整改记录
     */
    boolean delete(Long id);
}
