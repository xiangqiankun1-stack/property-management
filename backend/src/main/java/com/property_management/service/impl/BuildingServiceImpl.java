package com.property_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.property_management.dao.Building;
import com.property_management.dao.House;
import com.property_management.mapper.BuildingMapper;
import com.property_management.mapper.HouseMapper;
import com.property_management.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private HouseMapper houseMapper;

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
    @Transactional
    public boolean delete(Long id) {
        // 1. 删除关联的房屋
        LambdaQueryWrapper<House> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(House::getBuildingId, id);
        houseMapper.delete(wrapper);

        // 2. 删除楼栋（逻辑删除）
        return buildingMapper.deleteById(id) > 0;
    }
}