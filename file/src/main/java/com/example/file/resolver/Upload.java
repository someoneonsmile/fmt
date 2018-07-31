package com.example.file.resolver;

import java.lang.annotation.*;

/**
 * @author wangyp
 */
@Target( {ElementType.PARAMETER} )
@Retention( RetentionPolicy.RUNTIME )
@Documented
public @interface Upload {

    /**
     * 上传文件的参数名
     */
    String value() default "";
}
