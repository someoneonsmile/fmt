package com.closer.ws.common;

/**
 * <p>
 * 单例类(静态内部枚举)<br>
 * 内部类来延迟加载一个枚举类<br>
 * 返回的单例对象为： 内部枚举<br>
 * </p>
 * <p>
 * 静态内部类单例：<br>
 * 一般为通过静态内部类<br>
 * 延迟创建外部类的一个对象<br>
 * </p>
 * <p>
 * 总结：<br>
 * 内部类单例本质是通过类加载机制延迟创建对象<br>
 * 创建的对象是外部类对象，还是内部类对象都可以<br>
 * 而枚举创建的是本类对象，所以内部枚举类，返回的是内部枚举类对象
 * </p>
 */
public class Single {

    public static Inner success() {
        return Inner.success;
    }

    public static Inner fail() {
        return Inner.fail;
    }

    /**
     * 静态内部枚举
     */
    public static enum Inner {
        success(true, 200, "success", null),
        fail(false, 500, "fail", null);
        private boolean suc;
        private int code;
        private String msg;
        private Object data;

        Inner(boolean success, int code, String msg, Object data) {
            this.suc = success;
            this.code = code;
            this.msg = msg;
            this.data = data;
        }
    }
}
