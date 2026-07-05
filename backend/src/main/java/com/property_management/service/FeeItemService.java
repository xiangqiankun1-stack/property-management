package com.property_management.service;

import com.property_management.dao.FeeItem;

import java.util.List;

public interface FeeItemService {

    List<FeeItem> getAll();

    FeeItem getById(Long id);

    boolean add(FeeItem feeItem);

    boolean update(FeeItem feeItem);

    boolean delete(Long id);
}
