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
@RequestMapping("/userRole")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    //添加用户角色
    @PostMapping("/addUserRole")
    public R<Void> addUserRole(@Validated @RequestBody SysUserRole sysUserRole){
        try {
            userRoleService.addUserRole(sysUserRole);
        }catch (Exception e) {
            return R.fail(FailResultCode.FAIL.getCode(),e.getMessage());
        } finally {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "添加成功");
        }
    }

    //删除用户角色
    @PostMapping("/deleteUserRole")
    public R<Void> deleteUserRole(@RequestBody SysUserRole sysUserRole){
        userRoleService.deleteUserRole(sysUserRole);
        return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
    }

    //修改用户角色
    @PostMapping("/updateUserRole")
    public R<Void> updateUserRole(@Validated @RequestBody SysUserRole sysUserRole){
        userRoleService.updateUserRole(sysUserRole);
        return R.success(SuccessResultCode.SUCCESS.getCode(), "修改成功");
    }

    //获取用户角色
    @GetMapping("/getUserRole")
    public R<List<String>> getUserRole(@RequestParam Long id){
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功",
                userRoleService.getUserRole(id));
    }
}
