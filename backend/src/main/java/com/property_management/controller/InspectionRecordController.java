package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.InspectionRecord;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.InspectionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inspection-records")
public class InspectionRecordController {

    @Autowired
    private InspectionRecordService inspectionRecordService;

    /**
     * 获取巡检整改记录列表
     * GET /api/v1/inspection-records
     */
    @GetMapping
    public R<List<InspectionRecord>> list() {
        List<InspectionRecord> list = inspectionRecordService.getAll();
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }

    /**
     * 获取单条巡检整改记录
     * GET /api/v1/inspection-records/{id}
     */
    @GetMapping("/{id}")
    public R<InspectionRecord> getById(@PathVariable Long id) {
        InspectionRecord record = inspectionRecordService.getById(id);
        if (record == null) {
            return R.fail(FailResultCode.FAIL.getCode(), "巡检整改记录不存在");
        }
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", record);
    }

    /**
     * 新增巡检整改记录
     * POST /api/v1/inspection-records
     */
    @PostMapping
    public R<Void> add(@Validated @RequestBody InspectionRecord inspectionRecord) {
        boolean result = inspectionRecordService.add(inspectionRecord);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "新增成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "新增失败");
    }

    /**
     * 更新巡检整改记录
     * PUT /api/v1/inspection-records/{id}
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody InspectionRecord inspectionRecord) {
        inspectionRecord.setId(id);
        boolean result = inspectionRecordService.update(inspectionRecord);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "更新成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "更新失败");
    }

    /**
     * 删除巡检整改记录
     * DELETE /api/v1/inspection-records/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean result = inspectionRecordService.delete(id);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "删除失败");
    }
}
