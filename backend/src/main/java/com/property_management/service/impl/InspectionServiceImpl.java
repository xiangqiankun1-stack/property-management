package com.property_management.service.impl;

import com.property_management.dao.Inspection;
import com.property_management.mapper.InspectionMapper;
import com.property_management.service.InspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InspectionServiceImpl implements InspectionService {

    @Autowired
    private InspectionMapper inspectionMapper;

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
    public boolean delete(Long id) {
        return inspectionMapper.deleteById(id) > 0;
    }
}
