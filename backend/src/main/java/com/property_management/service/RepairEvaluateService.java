package com.property_management.service;

import com.property_management.dao.RepairEvaluate;

import java.util.List;

public interface RepairEvaluateService {

    List<RepairEvaluate> getAll();

    RepairEvaluate getById(Long id);

    boolean add(RepairEvaluate repairEvaluate);

    boolean update(RepairEvaluate repairEvaluate);

    boolean delete(Long id);
}
