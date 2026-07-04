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
    public void addHouse(House house) {
        houseMapper.insert(house);
    }

    @Override
    public void deleteHouse(House house) {
        houseMapper.deleteById(house.getId());
    }

    @Override
    public void updateHouse(House house) {
        houseMapper.updateById(house);
    }

    @Override
    public House getDetail(Long id) {
        return houseMapper.selectById(id);
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
