package com.property_management.service.impl;

import com.property_management.dao.RepairEvaluate;
import com.property_management.mapper.RepairEvaluateMapper;
import com.property_management.service.RepairEvaluateService;
import com.property_management.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RepairEvaluateServiceImpl implements RepairEvaluateService {

    @Autowired
    private RepairEvaluateMapper repairEvaluateMapper;

    @Override
    public List<RepairEvaluate> getAll() {
        return repairEvaluateMapper.selectList(null);
    }

    @Override
    public RepairEvaluate getById(Long id) {
        return repairEvaluateMapper.selectById(id);
    }

    @Override
    public boolean add(RepairEvaluate repairEvaluate) {
        Long currentUserId = ThreadLocalUtil.getCurrentUserId();
        if (currentUserId == null) {
            currentUserId = 0L;
        }
        repairEvaluate.setOwnerId(currentUserId);
        return repairEvaluateMapper.insert(repairEvaluate) > 0;
    }

    @Override
    public boolean update(RepairEvaluate repairEvaluate) {
        return repairEvaluateMapper.updateById(repairEvaluate) > 0;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        // 评价记录没有子表关联，直接删除（逻辑删除）
        return repairEvaluateMapper.deleteById(id) >= 0;
    }
}