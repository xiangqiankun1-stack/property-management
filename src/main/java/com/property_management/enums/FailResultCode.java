package com.property_management.enums;

public enum FailResultCode {
    FAIL(400, "操作失败"),
    AUTHENTICATION_FAIL(401, "认证失败"),
    REFRESH_ACCESS_TOKEN_FAIL(403, "刷新token失败"),
    LOGOUT_FAIL(400, "登出失败");

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    FailResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
