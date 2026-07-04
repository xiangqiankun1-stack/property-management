package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.SysRoleInfo;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 获取角色列表
     * GET /api/v1/roles
     */
    @GetMapping
    public R<List<SysRoleInfo>> list() {
        List<SysRoleInfo> list = roleService.getAll();
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }

    /**
     * 获取单个角色
     * GET /api/v1/roles/{id}
     */
    @GetMapping("/{id}")
    public R<SysRoleInfo> getById(@PathVariable Long id) {
        SysRoleInfo role = roleService.getById(id);
        if (role == null) {
            return R.fail(FailResultCode.FAIL.getCode(), "角色不存在");
        }
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", role);
    }

    /**
     * 新增角色
     * POST /api/v1/roles
     */
    @PostMapping
    public R<Void> add(@Validated @RequestBody SysRoleInfo sysRoleInfo) {
        boolean result = roleService.add(sysRoleInfo);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "新增成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "新增失败");
    }

    /**
     * 更新角色
     * PUT /api/v1/roles/{id}
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody SysRoleInfo sysRoleInfo) {
        sysRoleInfo.setId(id);
        boolean result = roleService.update(sysRoleInfo);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "更新成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "更新失败");
    }

    /**
     * 删除角色
     * DELETE /api/v1/roles/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean result = roleService.delete(id);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "删除失败");
    }
}
