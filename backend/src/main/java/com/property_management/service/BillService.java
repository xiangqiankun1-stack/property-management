package com.property_management.service;

import com.property_management.dao.Bill;

import java.util.List;

public interface BillService {

    List<Bill> getAll();

    Bill getById(Long id);

    boolean add(Bill bill);

    boolean update(Bill bill);

    boolean delete(Long id);
}
