package com.closer.ws.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * restful 返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResData<T> {

    private boolean success;
    private Integer code;
    private String msg;
    private T data;

    public static <E> ResData<E> success(){
        return new ResData<E>(true, 200, "成功", null);
    }

    public static <E> ResData<E> fail(){
        return new ResData<E>(false, 500, "服务繁忙", null);
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
