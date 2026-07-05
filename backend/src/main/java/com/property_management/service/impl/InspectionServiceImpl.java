package com.property_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.property_management.dao.Inspection;
import com.property_management.dao.InspectionRecord;
import com.property_management.mapper.InspectionMapper;
import com.property_management.mapper.InspectionRecordMapper;
import com.property_management.service.InspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InspectionServiceImpl implements InspectionService {

    @Autowired
    private InspectionMapper inspectionMapper;

    @Autowired
    private InspectionRecordMapper inspectionRecordMapper;

    @Override
    public List<Inspection> getAll() {
        return inspectionMapper.selectList(null);
    }

    @Override
    public Inspection getById(Long id) {
        return inspectionMapper.selectById(id);
    }

    @Override
    public boolean add(Inspection inspection) {
        return inspectionMapper.insert(inspection) > 0;
    }

    @Override
    public boolean update(Inspection inspection) {
        return inspectionMapper.updateById(inspection) > 0;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        // 1. 删除关联的巡检记录
        LambdaQueryWrapper<InspectionRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InspectionRecord::getInspectionId, id);
        inspectionRecordMapper.delete(wrapper);

        // 2. 删除巡检任务（逻辑删除）
        return inspectionMapper.deleteById(id) > 0;
    }
}