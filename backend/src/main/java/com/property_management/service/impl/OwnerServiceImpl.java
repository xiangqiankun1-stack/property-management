package com.property_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.property_management.dao.*;
import com.property_management.mapper.*;
import com.property_management.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerMapper ownerMapper;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private PaymentRecordMapper paymentRecordMapper;

    @Autowired
    private ComplaintMapper complaintMapper;

    @Autowired
    private RepairMapper repairMapper;

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
    @Transactional
    public void deleteOwner(Long id) {
        // 1. 删除关联的账单
        LambdaQueryWrapper<Bill> billWrapper = new LambdaQueryWrapper<>();
        billWrapper.eq(Bill::getOwnerId, id);
        billMapper.delete(billWrapper);

        // 2. 删除关联的缴费记录
        LambdaQueryWrapper<PaymentRecord> paymentWrapper = new LambdaQueryWrapper<>();
        paymentWrapper.eq(PaymentRecord::getOwnerId, id);
        paymentRecordMapper.delete(paymentWrapper);

        // 3. 删除关联的投诉
        LambdaQueryWrapper<Complaint> complaintWrapper = new LambdaQueryWrapper<>();
        complaintWrapper.eq(Complaint::getOwnerId, id);
        complaintMapper.delete(complaintWrapper);

        // 4. 删除关联的报修
        LambdaQueryWrapper<Repair> repairWrapper = new LambdaQueryWrapper<>();
        repairWrapper.eq(Repair::getOwnerId, id);
        repairMapper.delete(repairWrapper);

        // 5. 删除业主（逻辑删除）
        ownerMapper.deleteById(id);
    }

    @Override
    public void updateOwner(Owner owner) {
        ownerMapper.updateById(owner);
    }
}