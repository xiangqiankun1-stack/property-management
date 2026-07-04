package com.property_management.service.impl;

import com.property_management.dao.House;
import com.property_management.dao.HouseDTO;
import com.property_management.mapper.HouseMapper;
import com.property_management.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Override
    public List<House> getAll() {
        return houseMapper.selectList(null);
    }

    @Override
    public House getById(Long id) {
        return houseMapper.selectById(id);
    }

    @Override
    public boolean add(House house) {
        return houseMapper.insert(house) > 0;
    }

    @Override
    public boolean update(House house) {
        return houseMapper.updateById(house) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return houseMapper.deleteById(id) >= 0;
    }

    @Override
    public HouseDTO getDetailWithCommunityAndBuilding(Long id) {
        return houseMapper.getDetailWithCommunityAndBuilding(id);
    }

    @Override
    public List<HouseDTO> getAllWithCommunityAndBuilding() {
        return houseMapper.getAllWithCommunityAndBuilding();
    }
}
