package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.SysUserInfo;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.UserService;
import com.property_management.utils.JwtUtil;
import com.property_management.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * POST /api/v1/users/register
     */
    @PostMapping("/register")
    public R<Void> register(@Validated @RequestBody SysUserInfo sysUserInfo) {
        SysUserInfo u = userService.findByUserName(sysUserInfo.getUserName());
        if (u == null) {
            String password = sysUserInfo.getPassword();
            sysUserInfo.setPassword(MD5Util.getMD5String(password));
            boolean result = userService.register(sysUserInfo);
            if (result) {
                return R.success(SuccessResultCode.SUCCESS.getCode(), "注册成功");
            }
            return R.fail(FailResultCode.FAIL.getCode(), "注册失败");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "用户名已占用");
    }

    /**
     * 用户登录
     * POST /api/v1/users/login
     */
    @PostMapping("/login")
    public R<String> login(@RequestParam String username, @RequestParam String password) {
        SysUserInfo loginUser = userService.findByUserName(username);
        if (loginUser == null) {
            return R.fail(FailResultCode.FAIL.getCode(), "用户名错误");
        }
        if (MD5Util.getMD5String(password).equals(loginUser.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUserName());
            String token = JwtUtil.genToken(claims);
            return R.success(SuccessResultCode.AUTHENTICATION_SUCCESS.getCode(), "登录成功", token);
        }
        return R.fail(FailResultCode.FAIL.getCode(), "密码错误");
    }

    /**
     * 获取用户列表
     * GET /api/v1/users
     */
    @GetMapping
    public R<List<SysUserInfo>> list() {
        List<SysUserInfo> list = userService.getAll();
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }

    /**
     * 获取单个用户
     * GET /api/v1/users/{id}
     */
    @GetMapping("/{id}")
    public R<SysUserInfo> getById(@PathVariable Long id) {
        SysUserInfo user = userService.getById(id);
        if (user == null) {
            return R.fail(FailResultCode.FAIL.getCode(), "用户不存在");
        }
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", user);
    }

    /**
     * 新增用户
     * POST /api/v1/users
     */
    @PostMapping
    public R<Void> add(@Validated @RequestBody SysUserInfo sysUserInfo) {
        boolean result = userService.add(sysUserInfo);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "新增成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "新增失败");
    }

    /**
     * 更新用户
     * PUT /api/v1/users/{id}
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody SysUserInfo sysUserInfo) {
        sysUserInfo.setId(id);
        boolean result = userService.update(sysUserInfo);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "更新成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "更新失败");
    }

    /**
     * 删除用户
     * DELETE /api/v1/users/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean result = userService.delete(id);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "删除失败");
    }
}
