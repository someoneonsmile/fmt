package com.example.basedemo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

/**
 * Spring工具类
 */
@Slf4j
@Configuration
@ServletComponentScan
public class SpringUtil {

    /**
     * 保持Spring上下文
     */
    private static ApplicationContext ctx = null;

    /**
     * 保持Servlet上下文
     */
    private static ServletContext sct = null;


    @WebListener
    public static class ContextListener extends ContextLoaderListener {

        @Override
        public void contextInitialized( ServletContextEvent sce ) {
            log.info("startup listener  ........");
            ServletContext servletContext = sce.getServletContext();
            SpringUtil.initial(servletContext, WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext));
            log.info("startup listener success ........");
        }
    }


    /**
     * 初始化上下文
     * @param newSct
     *     Servlet上下文
     * @param newCtx
     *     系统启动时传入的上下文
     */
    public static void initial( ServletContext newSct, ApplicationContext newCtx ) {
        ctx = newCtx;
        sct = newSct;
        log.info("SpringUtil initial successful!");
    }


    /**
     * 获取应用上下文
     */
    public static ApplicationContext getCtx() {
        return ctx;
    }


    /**
     * 获取Servlet上下文
     */
    public static ServletContext getSct() {
        return sct;
    }


    /**
     * 根据名称和类型获取Bean实例
     * @param clazz
     *     Bean类
     * @param <T>
     *     指定类型
     * @return Bean实例
     */
    public static <T> T getBean( Class<T> clazz ) {
        if ( ctx == null ) {
            log.warn("SpringUtil has not been initialed!");
            return null;
        }
        return ctx.getBean(clazz);
    }
}