package com.property_management.service;

import com.property_management.dao.Owner;

import java.util.List;

public interface OwnerService {
    List<Owner> getAll();

    Owner getById(Long id);

    void addOwner(Owner owner);

    void deleteOwner(Long id);

    void updateOwner(Owner owner);
}
