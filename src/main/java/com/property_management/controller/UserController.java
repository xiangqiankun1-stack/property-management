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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 用户注册
    @PostMapping("/register")
    public R<Void> register(@Validated @RequestBody SysUserInfo sysUserInfo) {
        //查询用户
        SysUserInfo u = userService.findByUserName(sysUserInfo.getUserName());
        if (u == null) {

            //密码加密
            String password = sysUserInfo.getPassword();
            sysUserInfo.setPassword(MD5Util.getMD5String(password));

            //没有占用
            //注册
            userService.register(sysUserInfo);
            return R.success(SuccessResultCode.SUCCESS.getCode(), "注册成功");
        } else {
            //占用
            return R.fail(FailResultCode.FAIL.getCode(), "用户名已占用");
        }
    }

    //用户登录
    @PostMapping("/login")
    public R<String> login(@RequestParam String username, @RequestParam String password){
        //根据用户名查询用户
        SysUserInfo loginUser = userService.findByUserName(username);
        //判断用户是否存在
        if (loginUser == null) {
            return R.fail(FailResultCode.FAIL.getCode(),"用户名错误");
        }
        //判断密码是否正确
        if (MD5Util.getMD5String(password).equals(loginUser.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUserName());
            String token = JwtUtil.genToken(claims);
            return R.success(SuccessResultCode.AUTHENTICATION_SUCCESS.getCode(), token);
        }
        return R.fail(FailResultCode.FAIL.getCode(),"密码错误");
    }

    //获取用户信息
    @GetMapping("/getInfos")
    public R<List<SysUserInfo>> getInfos(){
        List<SysUserInfo> list = userService.getAlls();
        if (list == null)
            return R.fail(FailResultCode.FAIL.getCode(),"获取失败");
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }
}
