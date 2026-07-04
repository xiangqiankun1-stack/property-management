package com.property_management.service.impl;

import com.property_management.dao.Community;
import com.property_management.mapper.CommunityMapper;
import com.property_management.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

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
    public boolean delete(Long id) {
        return communityMapper.deleteById(id) > 0;
    }
}
