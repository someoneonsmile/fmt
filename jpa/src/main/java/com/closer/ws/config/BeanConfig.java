package com.closer.ws.config;

import com.closer.ws.common.CrudBaseEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class BeanConfig {

    @Bean
    @Scope(value="prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public CrudBaseEntity baseEntity(){
        return new CrudBaseEntity();
    }

}
