package com.property_management.common;

import lombok.Data;

@Data
public class R<T> {
    private Integer code;
    private String message;
    private T data;

    public static R success(Integer code, String message) {
        R result = new R();
        result.code = code;
        result.message = message;
        return result;
    }

    public static <T> R<T> success(Integer code, String message, T data) {
        R<T> result = new R<T>();
        result.code = code;
        result.message = message;
        result.data = data;
        return result;
    }

    public static R fail(Integer code, String message) {
        R result = new R();
        result.code = code;
        result.message = message;
        return result;
    }

    public static <T> R<T> fail(Integer code, String message, T data) {
        R<T> result = new R<T>();
        result.code = code;
        result.message = message;
        result.data = data;
        return result;
    }
}
