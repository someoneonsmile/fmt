package com.example.basedemo.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wangyp
 */
@Slf4j
@ControllerAdvice
public class ErrorController {

    @ExceptionHandler( Exception.class )
    @ResponseBody
    public ResData handleException( Exception e ) {
        log.error("全局异常", e);
        return ResData.ofFail(e.getMessage(), e);
    }
}
