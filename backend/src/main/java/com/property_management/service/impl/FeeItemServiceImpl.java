package com.property_management.service.impl;

import com.property_management.dao.FeeItem;
import com.property_management.mapper.FeeItemMapper;
import com.property_management.service.FeeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return feeItemMapper.insert(feeItem) > 0;
    }

    @Override
    public boolean update(FeeItem feeItem) {
        return feeItemMapper.updateById(feeItem) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return feeItemMapper.deleteById(id) >= 0;
    }
}
