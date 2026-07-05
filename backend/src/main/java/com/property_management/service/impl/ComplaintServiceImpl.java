package com.property_management.service.impl;

import com.property_management.dao.Complaint;
import com.property_management.mapper.ComplaintMapper;
import com.property_management.service.ComplaintService;
import com.property_management.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintMapper complaintMapper;

    @Override
    public List<Complaint> getAll() {
        return complaintMapper.selectList(null);
    }

    @Override
    public Complaint getById(Long id) {
        return complaintMapper.selectById(id);
    }

    @Override
    public boolean add(Complaint complaint) {
        Long currentUserId = ThreadLocalUtil.getCurrentUserId();
        if (currentUserId == null) {
            currentUserId = 0L;
        }
        complaint.setOwnerId(currentUserId);
        return complaintMapper.insert(complaint) > 0;
    }

    @Override
    public boolean update(Complaint complaint) {
        return complaintMapper.updateById(complaint) > 0;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        // 投诉记录没有子表关联，直接删除（逻辑删除）
        return complaintMapper.deleteById(id) >= 0;
    }

    @Override
    public boolean handle(Complaint complaint) {
        complaint.setHandleTime(LocalDateTime.now());
        Long currentUserId = ThreadLocalUtil.getCurrentUserId();
        if (currentUserId == null) {
            currentUserId = 0L;
        }
        complaint.setHandleUserId(currentUserId);
        return complaintMapper.updateById(complaint) > 0;
    }
}