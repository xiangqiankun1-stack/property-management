package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.PaymentRecord;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.PaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-records")
public class PaymentRecordController {

    @Autowired
    private PaymentRecordService paymentRecordService;

    /**
     * 获取缴费记录列表
     * GET /api/v1/payment-records
     */
    @GetMapping
    public R<List<PaymentRecord>> list() {
        List<PaymentRecord> list = paymentRecordService.getAll();
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }

    /**
     * 获取单条缴费记录
     * GET /api/v1/payment-records/{id}
     */
    @GetMapping("/{id}")
    public R<PaymentRecord> getById(@PathVariable Long id) {
        PaymentRecord paymentRecord = paymentRecordService.getById(id);
        if (paymentRecord == null) {
            return R.fail(FailResultCode.FAIL.getCode(), "缴费记录不存在");
        }
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", paymentRecord);
    }

    /**
     * 新增缴费记录
     * POST /api/v1/payment-records
     */
    @PostMapping
    public R<Void> add(@Validated @RequestBody PaymentRecord paymentRecord) {
        boolean result = paymentRecordService.add(paymentRecord);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "新增成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "新增失败");
    }

    /**
     * 更新缴费记录
     * PUT /api/v1/payment-records/{id}
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody PaymentRecord paymentRecord) {
        paymentRecord.setId(id);
        boolean result = paymentRecordService.update(paymentRecord);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "更新成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "更新失败");
    }

    /**
     * 删除缴费记录
     * DELETE /api/v1/payment-records/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean result = paymentRecordService.delete(id);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "删除失败");
    }
}
