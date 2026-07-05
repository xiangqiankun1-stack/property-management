package com.property_management.service.impl;

import com.property_management.dao.Repair;
import com.property_management.mapper.RepairMapper;
import com.property_management.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    private RepairMapper repairMapper;

    @Override
    public List<Repair> getAll() {
        return repairMapper.selectList(null);
    }

    @Override
    public Repair getById(Long id) {
        return repairMapper.selectById(id);
    }

    @Override
    public boolean add(Repair repair) {
        return repairMapper.insert(repair) > 0;
    }

    @Override
    public boolean update(Repair repair) {
        return repairMapper.updateById(repair) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return repairMapper.deleteById(id) > 0;
    }
}
