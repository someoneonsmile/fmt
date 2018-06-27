package com.closer.ws.common;

import lombok.Builder;
import lombok.Data;

/**
 * restful 返回对象
 */
@Data
@Builder
public class ResData<T> {

    private boolean success;

    private int code;

    private String msg;

    private T data;

    private ResData(){}

    /**
     * 成功(一般会返回数据)
     */
    public static <E> ResData<E> ofSuccess( E data ) {
        return ResData.<E>builder().success(true).data(data).build();
    }


    public static <E> ResData<E> ofSuccess( String msg, E data ) {
        return ResData.<E>builder().success(true).msg(msg).data(data).build();
    }


    public static <E> ResData<E> ofSuccess( int code, E data ) {
        return ResData.<E>builder().success(true).code(code).data(data).build();
    }


    public static <E> ResData<E> ofSuccess( int code, String msg, E data ) {
        return ResData.<E>builder().success(true).code(code).msg(msg).data(data).build();
    }


    /**
     * 失败(一般会有提示/错误码)
     */
    public static <E> ResData<E> ofFail( int code ) {
        return ResData.<E>builder().success(false).code(code).build();
    }


    /**
     * 失败(一般会有提示/错误码)
     */
    public static <E> ResData<E> ofFail( String msg ) {
        return ResData.<E>builder().success(false).msg(msg).build();
    }


    public static <E> ResData<E> ofFail( int code, String msg ) {
        return ResData.<E>builder().success(false).code(code).msg(msg).build();
    }


    public static <E> ResData<E> ofFail( int code, E data ) {
        return ResData.<E>builder().success(false).code(code).data(data).build();
    }


    public static <E> ResData<E> ofFail( String msg, E data ) {
        return ResData.<E>builder().success(false).msg(msg).data(data).build();
    }


    public static <E> ResData<E> ofFail( int code, String msg, E data ) {
        return ResData.<E>builder().success(false).code(code).msg(msg).data(data).build();
    }


    public static ResData defaultSuccess() {
        return InnerClass.SUCCESS;
    }


    public static ResData defaultFail() {
        return InnerClass.FAIL;
    }


    /**
     * 延迟加载类
     */
    private static class InnerClass {

        private static final ResData SUCCESS = ResData.builder().success(true).build();

        private static final ResData FAIL = ResData.builder().success(false).code(500).msg("服务异常").build();
    }
}
