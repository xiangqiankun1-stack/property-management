package com.property_management.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_info")
public class SysUserInfo extends BaseEntity {

    /**
     * 用户名（登录账号）
     */
    @NotBlank(message = "用户名不能为空")
    private String userName;

    /**
     * 登录密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phoneNumber;

    /**
     * 头像地址
     */
    private String avatarAddress;

    /**
     * 姓名
     */
    @NotBlank(message = "昵称不能为空")
    private String fullName;

}