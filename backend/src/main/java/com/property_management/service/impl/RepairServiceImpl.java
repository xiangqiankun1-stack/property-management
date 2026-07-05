package com.property_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.property_management.dao.Repair;
import com.property_management.dao.RepairEvaluate;
import com.property_management.dao.RepairOrder;
import com.property_management.mapper.RepairEvaluateMapper;
import com.property_management.mapper.RepairMapper;
import com.property_management.mapper.RepairOrderMapper;
import com.property_management.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    private RepairMapper repairMapper;

    @Autowired
    private RepairOrderMapper repairOrderMapper;

    @Autowired
    private RepairEvaluateMapper repairEvaluateMapper;

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
    @Transactional
    public boolean delete(Long id) {
        // 1. 删除关联的派单记录
        LambdaQueryWrapper<RepairOrder> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.eq(RepairOrder::getRepairId, id);
        repairOrderMapper.delete(orderWrapper);

        // 2. 删除关联的评价
        LambdaQueryWrapper<RepairEvaluate> evaluateWrapper = new LambdaQueryWrapper<>();
        evaluateWrapper.eq(RepairEvaluate::getRepairId, id);
        repairEvaluateMapper.delete(evaluateWrapper);

        // 3. 删除报修记录（逻辑删除）
        return repairMapper.deleteById(id) > 0;
    }
}