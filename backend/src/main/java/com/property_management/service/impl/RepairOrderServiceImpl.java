package com.property_management.service.impl;

import com.property_management.dao.RepairOrder;
import com.property_management.mapper.RepairOrderMapper;
import com.property_management.service.RepairOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public boolean delete(Long id) {
        // 派单记录没有子表关联，直接删除（逻辑删除）
        return repairOrderMapper.deleteById(id) > 0;
    }
}