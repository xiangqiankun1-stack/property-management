package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.Inspection;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.InspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inspections")
public class InspectionController {

    @Autowired
    private InspectionService inspectionService;

    /**
     * 获取巡检任务列表
     * GET /api/v1/inspections
     */
    @GetMapping
    public R<List<Inspection>> list() {
        List<Inspection> list = inspectionService.getAll();
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }

    /**
     * 获取单个巡检任务
     * GET /api/v1/inspections/{id}
     */
    @GetMapping("/{id}")
    public R<Inspection> getById(@PathVariable Long id) {
        Inspection inspection = inspectionService.getById(id);
        if (inspection == null) {
            return R.fail(FailResultCode.FAIL.getCode(), "巡检任务不存在");
        }
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", inspection);
    }

    /**
     * 新增巡检任务
     * POST /api/v1/inspections
     */
    @PostMapping
    public R<Void> add(@Validated @RequestBody Inspection inspection) {
        boolean result = inspectionService.add(inspection);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "新增成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "新增失败");
    }

    /**
     * 更新巡检任务
     * PUT /api/v1/inspections/{id}
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody Inspection inspection) {
        inspection.setId(id);
        boolean result = inspectionService.update(inspection);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "更新成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "更新失败");
    }

    /**
     * 删除巡检任务
     * DELETE /api/v1/inspections/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean result = inspectionService.delete(id);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "删除失败");
    }
}
