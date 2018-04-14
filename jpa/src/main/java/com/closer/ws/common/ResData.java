package com.closer.ws.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * restful 返回对象
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResData<T> {

    private boolean success;
    private int code;
    private String msg;
    private T data;

    public static <E> ResData<E> success() {
        return new ResData<>(true, 200, "", null);
    }

    public static <E> ResData<E> success(String msg) {
        return new ResData<>(true, 200, msg, null);
    }

    public static <E> ResData<E> success(String msg, E data) {
        return new ResData<>(true, 200, msg, data);
    }

    public static <E> ResData<E> fail() {
        return new ResData<>(false, 500, "", null);
    }

    public static <E> ResData<E> fail(String msg) {
        return new ResData<>(false, 500, msg, null);
    }

    public static <E> ResData<E> fail(String msg, E data) {
        return new ResData<>(false, 500, msg, data);
    }

    public static <E> ResData<E> custom(boolean success, int code, String msg, E data) {
        return new ResData<>(success, code, msg, data);
    }

    public static ResData singleSuccess() {
        return InnerClass.success;
    }

    public static ResData singleFail() {
        return InnerClass.fail;
    }

    /**
     * 静态内部类
     */
    private static class InnerClass {
        private static final ResData success = new ResData<>(true, 200, "成功", null);
        private static final ResData fail = new ResData<>(false, 500, "服务繁忙", null);
    }
}
