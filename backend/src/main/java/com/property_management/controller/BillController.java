package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.Bill;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bills")
public class BillController {

    @Autowired
    private BillService billService;

    /**
     * 获取账单列表
     * GET /api/v1/bills
     */
    @GetMapping
    public R<List<Bill>> list() {
        List<Bill> list = billService.getAll();
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }

    /**
     * 获取单个账单
     * GET /api/v1/bills/{id}
     */
    @GetMapping("/{id}")
    public R<Bill> getById(@PathVariable Long id) {
        Bill bill = billService.getById(id);
        if (bill == null) {
            return R.fail(FailResultCode.FAIL.getCode(), "账单不存在");
        }
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", bill);
    }

    /**
     * 新增账单
     * POST /api/v1/bills
     */
    @PostMapping
    public R<Void> add(@Validated @RequestBody Bill bill) {
        boolean result = billService.add(bill);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "新增成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "新增失败");
    }

    /**
     * 更新账单
     * PUT /api/v1/bills/{id}
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody Bill bill) {
        bill.setId(id);
        boolean result = billService.update(bill);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "更新成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "更新失败");
    }

    /**
     * 删除账单
     * DELETE /api/v1/bills/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean result = billService.delete(id);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "删除失败");
    }
}
