package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.AssignPermissionRequest;
import com.property_management.dao.SysPermissionInfo;
import com.property_management.dao.SysRolePermission;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role-permission")
public class RolePermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 获取角色权限列表
     * GET /api/v1/role-permission/{roleId}/permissions
     */
    @GetMapping("/{roleId}/permissions")
    public R<List<SysPermissionInfo>> getRolePermissions(@PathVariable Long roleId) {
        List<SysPermissionInfo> list = rolePermissionService.getRolePermissionList(roleId);
        return R.success(SuccessResultCode.SUCCESS.getCode(), SuccessResultCode.SUCCESS.getMessage(), list);
    }

    /**
     * 获取所有权限（用于权限树）
     * GET /api/v1/role-permission/all
     */
    @GetMapping("/all")
    public R<List<SysPermissionInfo>> getAllPermissions() {
        List<SysPermissionInfo> list = rolePermissionService.getAllPermissions();
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }

    /**
     * 分配角色权限（完整替换）
     * POST /api/v1/role-permission/assign
     */
    @PostMapping("/assign")
    public R<Void> assignPermissions(@RequestBody AssignPermissionRequest request) {
        try {
            boolean result = rolePermissionService.assignPermissions(
                    request.getRoleId(),
                    request.getPermissionIds()
            );
            if (result) {
                return R.success(SuccessResultCode.SUCCESS.getCode(), "权限分配成功");
            }
            return R.fail(FailResultCode.FAIL.getCode(), "权限分配失败");
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail(FailResultCode.FAIL.getCode(), e.getMessage());
        }
    }

    /**
     * 新增角色权限
     * POST /api/v1/role-permission
     */
    @PostMapping()
    public R<Void> add(@RequestBody SysRolePermission sysRolePermission) {
        try {
            boolean result = rolePermissionService.add(sysRolePermission);
            if (result) {
                return R.success(SuccessResultCode.SUCCESS.getCode(), "新增成功");
            }
            return R.fail(FailResultCode.FAIL.getCode(), "新增失败");
        } catch (Exception e) {
            return R.fail(FailResultCode.FAIL.getCode(), FailResultCode.FAIL.getMessage());
        }
    }

    /**
     * 删除角色权限（根据ID）
     * DELETE /api/v1/role-permission/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean result = rolePermissionService.delete(id);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "删除失败");
    }

    /**
     * 删除角色的某个权限（根据角色ID和权限ID）
     * DELETE /api/v1/role-permission/role/{roleId}/permission/{permissionId}
     */
    @DeleteMapping("/role/{roleId}/permission/{permissionId}")
    public R<Void> deleteRolePermission(@PathVariable Long roleId, @PathVariable Long permissionId) {
        try {
            boolean result = rolePermissionService.deleteRolePermission(roleId, permissionId);
            if (result) {
                return R.success(SuccessResultCode.SUCCESS.getCode(), "权限删除成功");
            }
            return R.fail(FailResultCode.FAIL.getCode(), "权限删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail(FailResultCode.FAIL.getCode(), e.getMessage());
        }
    }

    /**
     * 修改角色权限
     * PUT /api/v1/role-permission/{id}
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SysRolePermission sysRolePermission) {
        boolean result = rolePermissionService.update(id, sysRolePermission);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "修改成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "修改失败");
    }
}

