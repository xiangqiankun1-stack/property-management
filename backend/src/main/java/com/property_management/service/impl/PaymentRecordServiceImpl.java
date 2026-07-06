package com.property_management.service.impl;

import com.property_management.dao.PaymentRecord;
import com.property_management.mapper.PaymentRecordMapper;
import com.property_management.service.PaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

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
        // 自动生成第三方支付流水号/凭证号：V + yyyyMMddHHmmss + 4位随机数
        String voucherNo = "V" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + String.format("%04d", new Random().nextInt(10000));
        paymentRecord.setVoucherNo(voucherNo);
        return paymentRecordMapper.insert(paymentRecord) > 0;
    }

    @Override
    public boolean update(PaymentRecord paymentRecord) {
        return paymentRecordMapper.updateById(paymentRecord) > 0;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        // 缴费记录没有子表关联，直接删除（逻辑删除）
        return paymentRecordMapper.deleteById(id) >= 0;
    }
}