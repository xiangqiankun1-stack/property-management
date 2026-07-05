package com.property_management.service.impl;

import com.property_management.dao.RepairOrder;
import com.property_management.mapper.RepairOrderMapper;
import com.property_management.service.RepairOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairOrderServiceImpl implements RepairOrderService {

    @Autowired
    private RepairOrderMapper repairOrderMapper;

    @Override
    public List<RepairOrder> getAll() {
        return repairOrderMapper.selectList(null);
    }

    @Override
    public RepairOrder getById(Long id) {
        return repairOrderMapper.selectById(id);
    }

    @Override
    public boolean add(RepairOrder repairOrder) {
        return repairOrderMapper.insert(repairOrder) > 0;
    }

    @Override
    public boolean update(RepairOrder repairOrder) {
        return repairOrderMapper.updateById(repairOrder) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return repairOrderMapper.deleteById(id) > 0;
    }
}
