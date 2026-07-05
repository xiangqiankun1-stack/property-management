package com.property_management.service.impl;

import com.property_management.dao.PaymentRecord;
import com.property_management.mapper.PaymentRecordMapper;
import com.property_management.service.PaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentRecordServiceImpl implements PaymentRecordService {

    @Autowired
    private PaymentRecordMapper paymentRecordMapper;

    @Override
    public List<PaymentRecord> getAll() {
        return paymentRecordMapper.selectList(null);
    }

    @Override
    public PaymentRecord getById(Long id) {
        return paymentRecordMapper.selectById(id);
    }

    @Override
    public boolean add(PaymentRecord paymentRecord) {
        return paymentRecordMapper.insert(paymentRecord) > 0;
    }

    @Override
    public boolean update(PaymentRecord paymentRecord) {
        return paymentRecordMapper.updateById(paymentRecord) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return paymentRecordMapper.deleteById(id) >= 0;
    }
}
