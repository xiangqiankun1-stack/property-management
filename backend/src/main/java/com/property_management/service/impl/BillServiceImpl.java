package com.property_management.service.impl;

import com.property_management.dao.Bill;
import com.property_management.mapper.BillMapper;
import com.property_management.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillMapper billMapper;

    @Override
    public List<Bill> getAll() {
        return billMapper.selectList(null);
    }

    @Override
    public Bill getById(Long id) {
        return billMapper.selectById(id);
    }

    @Override
    public boolean add(Bill bill) {
        return billMapper.insert(bill) > 0;
    }

    @Override
    public boolean update(Bill bill) {
        return billMapper.updateById(bill) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return billMapper.deleteById(id) >= 0;
    }
}
