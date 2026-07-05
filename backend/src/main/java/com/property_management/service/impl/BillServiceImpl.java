package com.property_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.property_management.dao.Bill;
import com.property_management.dao.PaymentRecord;
import com.property_management.mapper.BillMapper;
import com.property_management.mapper.PaymentRecordMapper;
import com.property_management.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private PaymentRecordMapper paymentRecordMapper;

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
    @Transactional
    public boolean delete(Long id) {
        // 1. 删除关联的缴费记录
        LambdaQueryWrapper<PaymentRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PaymentRecord::getBillId, id);
        paymentRecordMapper.delete(wrapper);

        // 2. 删除账单（逻辑删除）
        return billMapper.deleteById(id) >= 0;
    }
}