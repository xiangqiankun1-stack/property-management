package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.RepairOrder;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.RepairOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/repair-orders")
public class RepairOrderController {

    @Autowired
    private RepairOrderService repairOrderService;

    /**
     * 获取派单列表
     * GET /api/v1/repair-orders
     */
    @GetMapping
    public R<List<RepairOrder>> list() {
        List<RepairOrder> list = repairOrderService.getAll();
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }

    /**
     * 获取单条派单记录
     * GET /api/v1/repair-orders/{id}
     */
    @GetMapping("/{id}")
    public R<RepairOrder> getById(@PathVariable Long id) {
        RepairOrder repairOrder = repairOrderService.getById(id);
        if (repairOrder == null) {
            return R.fail(FailResultCode.FAIL.getCode(), "派单记录不存在");
        }
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", repairOrder);
    }

    /**
     * 新增派单记录
     * POST /api/v1/repair-orders
     */
    @PostMapping
    public R<Void> add(@Validated @RequestBody RepairOrder repairOrder) {
        boolean result = repairOrderService.add(repairOrder);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "新增成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "新增失败");
    }

    /**
     * 更新派单记录
     * PUT /api/v1/repair-orders/{id}
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody RepairOrder repairOrder) {
        repairOrder.setId(id);
        boolean result = repairOrderService.update(repairOrder);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "更新成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "更新失败");
    }

    /**
     * 删除派单记录
     * DELETE /api/v1/repair-orders/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean result = repairOrderService.delete(id);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "删除失败");
    }
}
