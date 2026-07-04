package com.property_management.service;

import com.property_management.dao.House;
import com.property_management.dao.HouseDTO;

import java.util.List;

public interface HouseService {
    List<House> getAll();

    void addHouse(House house);

    void deleteHouse(House house);

    void updateHouse(House house);

    House getDetail(Long id);

    HouseDTO getDetailWithCommunityAndBuilding(Long id);

    List<HouseDTO> getAllWithCommunityAndBuilding();
}
