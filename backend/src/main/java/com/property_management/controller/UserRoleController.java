package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.SysUserRole;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-roles")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 新增用户角色
     * POST /api/v1/user-roles
     */
    @PostMapping
    public R<Void> add(@Validated @RequestBody SysUserRole sysUserRole) {
        try {
            boolean result = userRoleService.add(sysUserRole);
            if (result) {
                return R.success(SuccessResultCode.SUCCESS.getCode(), "新增成功");
            }
            return R.fail(FailResultCode.FAIL.getCode(), "新增失败");
        } catch (Exception e) {
            return R.fail(FailResultCode.FAIL.getCode(), e.getMessage());
        }
    }

    /**
     * 更新用户角色
     * PUT /api/v1/user-roles/{id}
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody SysUserRole sysUserRole) {
        sysUserRole.setId(id);
        boolean result = userRoleService.update(sysUserRole);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "更新成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "更新失败");
    }

    /**
     * 删除用户角色
     * DELETE /api/v1/user-roles/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean result = userRoleService.delete(id);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "删除失败");
    }

    /**
     * 获取用户角色列表
     * GET /api/v1/user-roles/{userId}/roles
     */
    @GetMapping("/{userId}/roles")
    public R<List<String>> getUserRole(@PathVariable Long userId) {
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功",
                userRoleService.getUserRole(userId));
    }
}
