package com.property_management.service.impl;

import com.property_management.dao.InspectionRecord;
import com.property_management.mapper.InspectionRecordMapper;
import com.property_management.service.InspectionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InspectionRecordServiceImpl implements InspectionRecordService {

    @Autowired
    private InspectionRecordMapper inspectionRecordMapper;

    @Override
    public List<InspectionRecord> getAll() {
        return inspectionRecordMapper.selectList(null);
    }

    @Override
    public InspectionRecord getById(Long id) {
        return inspectionRecordMapper.selectById(id);
    }

    @Override
    public boolean add(InspectionRecord inspectionRecord) {
        return inspectionRecordMapper.insert(inspectionRecord) > 0;
    }

    @Override
    public boolean update(InspectionRecord inspectionRecord) {
        return inspectionRecordMapper.updateById(inspectionRecord) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return inspectionRecordMapper.deleteById(id) > 0;
    }
}
