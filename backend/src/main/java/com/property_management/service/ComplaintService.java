package com.property_management.service;

import com.property_management.dao.Complaint;
import com.property_management.dao.ComplaintHandleDTO;

import java.util.List;

public interface ComplaintService {

    List<Complaint> getAll();

    Complaint getById(Long id);

    boolean add(Complaint complaint);

    boolean update(Complaint complaint);

    boolean delete(Long id);

    boolean handle(Long id, ComplaintHandleDTO dto);
}
