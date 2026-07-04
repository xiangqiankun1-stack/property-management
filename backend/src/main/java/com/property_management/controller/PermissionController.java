package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.SysPermissionInfo;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 获取权限列表
     * GET /api/v1/permissions
     */
    @GetMapping
    public R<List<SysPermissionInfo>> list() {
        List<SysPermissionInfo> list = permissionService.getAll();
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }

    /**
     * 获取单个权限
     * GET /api/v1/permissions/{id}
     */
    @GetMapping("/{id}")
    public R<SysPermissionInfo> getById(@PathVariable Long id) {
        SysPermissionInfo permission = permissionService.getById(id);
        if (permission == null) {
            return R.fail(FailResultCode.FAIL.getCode(), "权限不存在");
        }
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", permission);
    }

    /**
     * 新增权限
     * POST /api/v1/permissions
     */
    @PostMapping
    public R<Void> add(@Validated @RequestBody SysPermissionInfo sysPermissionInfo) {
        boolean result = permissionService.add(sysPermissionInfo);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "新增成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "新增失败");
    }

    /**
     * 更新权限
     * PUT /api/v1/permissions/{id}
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody SysPermissionInfo sysPermissionInfo) {
        sysPermissionInfo.setId(id);
        boolean result = permissionService.update(sysPermissionInfo);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "更新成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "更新失败");
    }

    /**
     * 删除权限
     * DELETE /api/v1/permissions/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean result = permissionService.delete(id);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "删除失败");
    }
}
