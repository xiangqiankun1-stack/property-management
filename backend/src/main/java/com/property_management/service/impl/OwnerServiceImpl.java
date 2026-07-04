package com.property_management.service.impl;

import com.property_management.dao.Owner;
import com.property_management.mapper.OwnerMapper;
import com.property_management.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerMapper ownerMapper;

    @Override
    public List<Owner> getAll() {
        return ownerMapper.selectList(null);
    }

    @Override
    public Owner getById(Long id) {
        return ownerMapper.selectById(id);
    }

    @Override
    public void addOwner(Owner owner) {
        ownerMapper.insert(owner);
    }

    @Override
    public void deleteOwner(Long id) {
        ownerMapper.deleteById(id);
    }

    @Override
    public void updateOwner(Owner owner) {
        ownerMapper.updateById(owner);
    }
}
