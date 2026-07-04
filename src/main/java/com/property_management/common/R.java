package com.property_management.common;

import lombok.Data;

@Data
public class R<T> {
    /*
     * 状态码
     * */
    private Integer code;
    /*
     * 状态码对应的提示信息
     * */
    private String message;
    /*
     * 真正需要返回给前端的数据
     * */
    private T data;

    public static R success(Integer code, String message) {
        R r = new R();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }
    public static <T> R<T> success(Integer code, String message, T data) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMessage(message);
        r.setData(data);
        return r;
    }
    public static R fail(Integer code, String message) {
        R r = new R();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }
    public static <T> R<T> fail(Integer code, String message, T data) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMessage(message);
        r.setData(data);
        return r;
    }
}
