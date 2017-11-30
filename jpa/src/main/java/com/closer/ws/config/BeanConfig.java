package com.closer.ws.config;

import com.closer.ws.common.BaseEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.Random;

@Configuration
public class BeanConfig {

    @Bean
    @Scope(value="prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public BaseEntity baseEntity(){
        return new BaseEntity((long) new Random().nextInt(100));
    }

}
