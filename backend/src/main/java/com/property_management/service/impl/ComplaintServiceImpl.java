package com.property_management.service.impl;

import com.property_management.dao.Complaint;
import com.property_management.dao.ComplaintHandleDTO;
import com.property_management.mapper.ComplaintMapper;
import com.property_management.service.ComplaintService;
import com.property_management.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

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
        // 自动生成投诉编号：TS + yyyyMMddHHmmss + 4位随机数
        String complaintNo = "TS" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + String.format("%04d", new Random().nextInt(10000));
        complaint.setComplaintNo(complaintNo);

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
    public boolean handle(Long id, ComplaintHandleDTO dto) {
        Complaint complaint = complaintMapper.selectById(id);
        if (complaint == null) {
            return false;
        }
        complaint.setHandleTime(LocalDateTime.now());
        Long currentUserId = ThreadLocalUtil.getCurrentUserId();
        if (currentUserId == null) {
            currentUserId = 0L;
        }
        complaint.setHandleUserId(currentUserId);
        complaint.setHandleResult(dto.getHandleResult());
        complaint.setStatus(dto.getStatus());
        complaint.setSatisfaction(dto.getSatisfaction());
        return complaintMapper.updateById(complaint) > 0;
    }
}