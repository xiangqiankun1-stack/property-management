package com.property_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.property_management.dao.Building;
import com.property_management.mapper.BuildingMapper;
import com.property_management.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingMapper buildingMapper;

    @Override
    public List<Building> getAll() {
        return buildingMapper.selectList(null);
    }

    @Override
    public Building getById(Long id) {
        return buildingMapper.selectById(id);
    }

    @Override
    public boolean add(Building building) {
        return buildingMapper.insert(building) > 0;
    }

    @Override
    public boolean update(Building building) {
        return buildingMapper.updateById(building) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return buildingMapper.deleteById(id) > 0;
    }
}
