package com.property_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.property_management.dao.*;
import com.property_management.mapper.*;
import com.property_management.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private OwnerMapper ownerMapper;

    @Autowired
    private RepairMapper repairMapper;

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
    @Transactional
    public boolean delete(Long id) {
        // 1. 删除关联的账单
        LambdaQueryWrapper<Bill> billWrapper = new LambdaQueryWrapper<>();
        billWrapper.eq(Bill::getHouseId, id);
        billMapper.delete(billWrapper);

        // 2. 解除关联的业主的房屋绑定
        LambdaQueryWrapper<Owner> ownerWrapper = new LambdaQueryWrapper<>();
        ownerWrapper.eq(Owner::getHouseId, id);
        List<Owner> owners = ownerMapper.selectList(ownerWrapper);
        for (Owner owner : owners) {
            owner.setHouseId(null);
            ownerMapper.updateById(owner);
        }

        // 3. 删除关联的报修
        LambdaQueryWrapper<Repair> repairWrapper = new LambdaQueryWrapper<>();
        repairWrapper.eq(Repair::getHouseId, id);
        repairMapper.delete(repairWrapper);

        // 4. 删除房屋（逻辑删除）
        return houseMapper.deleteById(id) > 0;
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