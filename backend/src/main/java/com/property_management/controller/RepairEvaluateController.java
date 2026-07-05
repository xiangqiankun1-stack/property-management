package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.RepairEvaluate;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.RepairEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/repair-evaluate")
public class RepairEvaluateController {

    @Autowired
    private RepairEvaluateService repairEvaluateService;

    /**
     * 获取维修评价列表
     * GET /api/v1/repair-evaluate
     */
    @GetMapping
    public R<List<RepairEvaluate>> list() {
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功",
                repairEvaluateService.getAll());
    }

    /**
     * 获取单个维修评价
     * GET /api/v1/repair-evaluate/{id}
     */
    @GetMapping("/{id}")
    public R<RepairEvaluate> getById(@PathVariable Long id) {
        RepairEvaluate repairEvaluate = repairEvaluateService.getById(id);
        if (repairEvaluate == null) {
            return R.fail(FailResultCode.FAIL.getCode(), "评价不存在");
        }
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", repairEvaluate);
    }

    /**
     * 新增维修评价
     * POST /api/v1/repair-evaluate
     */
    @PostMapping
    public R<Void> add(@Validated @RequestBody RepairEvaluate repairEvaluate) {
        if (repairEvaluateService.add(repairEvaluate)) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "新增成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "新增失败");
    }

    /**
     * 更新维修评价
     * PUT /api/v1/repair-evaluate/{id}
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody RepairEvaluate repairEvaluate) {
        repairEvaluate.setId(id);
        if (repairEvaluateService.update(repairEvaluate)) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "更新成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "更新失败");
    }

    /**
     * 删除维修评价
     * DELETE /api/v1/repair-evaluate/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        if (repairEvaluateService.delete(id)) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "删除失败");
    }
}
