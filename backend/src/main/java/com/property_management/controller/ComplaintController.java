package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.Complaint;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    /**
     * 获取投诉列表
     * GET /api/v1/complaint
     */
    @GetMapping
    public R<List<Complaint>> list() {
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功",
                complaintService.getAll());
    }

    /**
     * 获取单个投诉
     * GET /api/v1/complaint/{id}
     */
    @GetMapping("/{id}")
    public R<Complaint> getById(@PathVariable Long id) {
        Complaint complaint = complaintService.getById(id);
        if (complaint == null) {
            return R.fail(FailResultCode.FAIL.getCode(), "投诉不存在");
        }
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", complaint);
    }

    /**
     * 新增投诉
     * POST /api/v1/complaint
     */
    @PostMapping
    public R<Void> add(@Validated @RequestBody Complaint complaint) {
        if (complaintService.add(complaint)) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "新增成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "新增失败");
    }

    /**
     * 更新投诉
     * PUT /api/v1/complaint/{id}
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody Complaint complaint) {
        complaint.setId(id);
        if (complaintService.update(complaint)) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "更新成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "更新失败");
    }

    /**
     * 处理投诉
     * PUT /api/v1/complaint/{id}/handle
     */
    @PutMapping("/{id}/handle")
    public R<Void> handle(@PathVariable Long id, @Validated @RequestBody Complaint complaint) {
        complaint.setId(id);
        if (complaintService.handle(complaint)) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "处理成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "处理失败");
    }

    /**
     * 删除投诉
     * DELETE /api/v1/complaint/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        if (complaintService.delete(id)) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "删除失败");
    }
}
