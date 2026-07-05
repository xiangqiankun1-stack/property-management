package com.property_management.service.impl;

import com.property_management.dao.FeeItem;
import com.property_management.mapper.FeeItemMapper;
import com.property_management.service.FeeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeeItemServiceImpl implements FeeItemService {

    @Autowired
    private FeeItemMapper feeItemMapper;

    @Override
    public List<FeeItem> getAll() {
        return feeItemMapper.selectList(null);
    }

    @Override
    public FeeItem getById(Long id) {
        return feeItemMapper.selectById(id);
    }

    @Override
    public boolean add(FeeItem feeItem) {
        Integer chargeMode = feeItem.getChargeMode();
        if (chargeMode == null) {
            return false;
        }
        feeItem.setUnit(switch (chargeMode) {
            case 1 -> "元/m²/月";
            case 2 -> "元/月";
            case 3 -> "元/车位/月";
            default -> "元";
        });
        return feeItemMapper.insert(feeItem) > 0;
    }

    @Override
    public boolean update(FeeItem feeItem) {
        Integer chargeMode = feeItem.getChargeMode();
        if (chargeMode == null) {
            return false;
        }
        feeItem.setUnit(switch (chargeMode) {
            case 1 -> "元/m²/月";
            case 2 -> "元/月";
            case 3 -> "元/车位/月";
            default -> "元";
        });
        return feeItemMapper.updateById(feeItem) > 0;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        // 费用项目没有子表关联，直接删除（逻辑删除）
        return feeItemMapper.deleteById(id) >= 0;
    }
}