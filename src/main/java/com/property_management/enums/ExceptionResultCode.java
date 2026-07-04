package com.property_management.enums;

public enum ExceptionResultCode {
    EXCEPTION(400, "操作异常"),
    AUTHENTICATION_EXCEPTION(401, "认证异常"),
    REFRESH_ACCESS_TOKEN_EXCEPTION(403, "刷新token异常"),
    LOGOUT_EXCEPTION(400, "登出异常");

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ExceptionResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
