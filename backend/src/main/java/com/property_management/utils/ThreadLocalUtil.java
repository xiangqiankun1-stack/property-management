package com.property_management.utils;

import java.util.Map;

public class ThreadLocalUtil {
    //提供ThreadLocal对象
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal<>();
    //根据键获取值
    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }
    //存储键值对
    public static void set(Object value) {
        THREAD_LOCAL.set(value);
    }
    //清除ThreadLocal 防止内存泄露
    public static void remove() {
        THREAD_LOCAL.remove();
    }

    /**
     * 获取当前用户信息
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getCurrentUser() {

        Object value = THREAD_LOCAL.get();

        if (value == null) {
            return null;
        }

        return (Map<String, Object>) value;
    }

    /**
     * 获取当前登录用户ID
     */
    public static Long getCurrentUserId() {
        Object value = THREAD_LOCAL.get();
        if (value == null) {
            return null;
        }

        if (value instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) value;
            Object id = map.get("id");
            if (id != null) {
                return Long.valueOf(id.toString());
            }
        }
        return null;
    }

    /**
     * 获取当前用户名
     */
    public static String getCurrentUsername() {

        Map<String, Object> userMap =
                getCurrentUser();

        if (userMap == null) {
            return null;
        }

        Object username = userMap.get("username");

        return username == null
                ? null
                : username.toString();
    }
}
