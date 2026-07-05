package com.property_management.service;

import com.property_management.dao.PaymentRecord;

import java.util.List;

public interface PaymentRecordService {

    List<PaymentRecord> getAll();

    PaymentRecord getById(Long id);

    boolean add(PaymentRecord paymentRecord);

    boolean update(PaymentRecord paymentRecord);

    boolean delete(Long id);
}
