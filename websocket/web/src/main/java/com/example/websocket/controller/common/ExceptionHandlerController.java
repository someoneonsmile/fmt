package com.example.websocket.controller.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangyp
 */
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler( NullPointerException.class )
    @ResponseStatus( HttpStatus.NOT_FOUND )
    public String processNullPointException( HttpServletRequest request, NullPointerException e ) {
        System.out.println("===应用到所有 @RequestMapping 注解的方法，在其抛出 NullPointerException 异常时执行");
        return "index"; //返回一个逻辑视图名
    }


    @ExceptionHandler( Exception.class )
    @ResponseStatus( HttpStatus.UNAUTHORIZED )
    public String processUnauthenticatedException( NativeWebRequest request, Exception e ) {
        System.out.println("===应用到所有 @RequestMapping 注解的方法，在其抛出 Exception 异常时执行");
        return "index"; //返回一个逻辑视图名
    }
}
