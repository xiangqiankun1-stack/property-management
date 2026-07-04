package com.property_management.enums;

public enum SuccessResultCode {
    SUCCESS(200, "操作成功"),
    AUTHENTICATION_SUCCESS(200, "认证成功"),
    REFRESH_ACCESS_TOKEN_SUCCESS(200, "刷新token成功"),
    LOGOUT_SUCCESS(200, "登出成功");

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    SuccessResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
