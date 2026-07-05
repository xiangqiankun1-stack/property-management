package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.FeeItem;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.FeeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fee-items")
public class FeeItemController {

    @Autowired
    private FeeItemService feeItemService;

    /**
     * 获取费用项目列表
     * GET /api/v1/fee-items
     */
    @GetMapping
    public R<List<FeeItem>> list() {
        List<FeeItem> list = feeItemService.getAll();
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }

    /**
     * 获取单个费用项目
     * GET /api/v1/fee-items/{id}
     */
    @GetMapping("/{id}")
    public R<FeeItem> getById(@PathVariable Long id) {
        FeeItem feeItem = feeItemService.getById(id);
        if (feeItem == null) {
            return R.fail(FailResultCode.FAIL.getCode(), "费用项目不存在");
        }
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", feeItem);
    }

    /**
     * 新增费用项目
     * POST /api/v1/fee-items
     */
    @PostMapping
    public R<Void> add(@Validated @RequestBody FeeItem feeItem) {
        boolean result = feeItemService.add(feeItem);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "新增成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "新增失败");
    }

    /**
     * 更新费用项目
     * PUT /api/v1/fee-items/{id}
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody FeeItem feeItem) {
        feeItem.setId(id);
        boolean result = feeItemService.update(feeItem);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "更新成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "更新失败");
    }

    /**
     * 删除费用项目
     * DELETE /api/v1/fee-items/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean result = feeItemService.delete(id);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "删除失败");
    }
}
