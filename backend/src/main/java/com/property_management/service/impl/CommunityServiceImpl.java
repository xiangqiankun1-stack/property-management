package com.property_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.property_management.dao.Building;
import com.property_management.dao.Community;
import com.property_management.mapper.BuildingMapper;
import com.property_management.mapper.CommunityMapper;
import com.property_management.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private BuildingMapper buildingMapper;

    @Override
    public List<Community> getAll() {
        return communityMapper.selectList(null);
    }

    @Override
    public Community getById(Long id) {
        return communityMapper.selectById(id);
    }

    @Override
    public boolean add(Community community) {
        return communityMapper.insert(community) > 0;
    }

    @Override
    public boolean update(Community community) {
        return communityMapper.updateById(community) > 0;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        // 1. 删除关联的楼栋
        LambdaQueryWrapper<Building> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Building::getCommunityId, id);
        buildingMapper.delete(wrapper);

        // 2. 删除小区（逻辑删除）
        return communityMapper.deleteById(id) > 0;
    }
}