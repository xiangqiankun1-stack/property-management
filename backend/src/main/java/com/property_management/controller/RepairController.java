package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.Repair;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/repairs")
public class RepairController {

    @Autowired
    private RepairService repairService;

    /**
     * 获取报修列表
     * GET /api/v1/repairs
     */
    @GetMapping
    public R<List<Repair>> list() {
        List<Repair> list = repairService.getAll();
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }

    /**
     * 获取单条报修记录
     * GET /api/v1/repairs/{id}
     */
    @GetMapping("/{id}")
    public R<Repair> getById(@PathVariable Long id) {
        Repair repair = repairService.getById(id);
        if (repair == null) {
            return R.fail(FailResultCode.FAIL.getCode(), "报修记录不存在");
        }
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", repair);
    }

    /**
     * 新增报修记录
     * POST /api/v1/repairs
     */
    @PostMapping
    public R<Void> add(@Validated @RequestBody Repair repair) {
        boolean result = repairService.add(repair);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "新增成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "新增失败");
    }

    /**
     * 更新报修记录
     * PUT /api/v1/repairs/{id}
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody Repair repair) {
        repair.setId(id);
        boolean result = repairService.update(repair);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "更新成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "更新失败");
    }

    /**
     * 删除报修记录
     * DELETE /api/v1/repairs/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean result = repairService.delete(id);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "删除失败");
    }
}
