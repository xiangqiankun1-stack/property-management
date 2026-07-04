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
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //获取所有角色
    @GetMapping("/getRoles")
    public R<List<SysRoleInfo>> getRoles(){
        List<SysRoleInfo> list = roleService.getAlls();
        if (list == null)
            return R.fail(FailResultCode.FAIL.getCode(),"获取失败");
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }

    //添加角色
    @PostMapping("/addRole")
    public R<Void> addRole(@Validated @RequestBody SysRoleInfo sysRoleInfo){
        //添加
        roleService.addRole(sysRoleInfo);
        return R.success(SuccessResultCode.SUCCESS.getCode(), "添加成功");
    }

    //删除角色
    @PostMapping("/deleteRole")
    public R<Void> deleteRole(@RequestBody SysRoleInfo sysRoleInfo){
        roleService.deleteRole(sysRoleInfo);
        return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
    }

    //修改角色
    @PostMapping("/updateRole")
    public R<Void> updateRole(@Validated @RequestBody SysRoleInfo sysRoleInfo){
        roleService.updateRole(sysRoleInfo);
        return R.success(SuccessResultCode.SUCCESS.getCode(), "修改成功");
    }
}
