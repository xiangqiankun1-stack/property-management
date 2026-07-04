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
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //获取权限列表
    @GetMapping("/getPermissions")
    public R<List<SysPermissionInfo>> getPermissions(){
        List<SysPermissionInfo> list = permissionService.getAlls();
        if (list == null)
            return R.fail(FailResultCode.FAIL.getCode(),"获取失败");
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }

    //添加权限
    @PostMapping("/addPermission")
    public R<Void> addPermission(@Validated @RequestBody SysPermissionInfo sysPermissionInfo){
        permissionService.addPermission(sysPermissionInfo);
        return R.success(SuccessResultCode.SUCCESS.getCode(), "添加成功");
    }

    //删除权限
    @PostMapping("/deletePermission")
    public R<Void> deletePermission(@RequestBody SysPermissionInfo sysPermissionInfo){
        permissionService.deletePermission(sysPermissionInfo);
        return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
    }

    //修改权限
    @PostMapping("/updatePermission")
    public R<Void> updatePermission(@Validated @RequestBody SysPermissionInfo sysPermissionInfo){
        permissionService.updatePermission(sysPermissionInfo);
        return R.success(SuccessResultCode.SUCCESS.getCode(), "修改成功");
    }
}
