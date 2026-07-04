package com.property_management.service;

import com.property_management.dao.House;
import com.property_management.dao.HouseDTO;

import java.util.List;

public interface HouseService {
    /**
     * 查询房屋详细信息，包括小区和楼栋等
     */
    HouseDTO getDetailWithCommunityAndBuilding(Long id);

    /**
     * 查询所有房屋详细信息，包括小区和楼栋等
     */
    List<HouseDTO> getAllWithCommunityAndBuilding();

    List<House> getAll();

    House getById(Long id);

    boolean add(House house);

    boolean update(House house);

    boolean delete(Long id);
}
