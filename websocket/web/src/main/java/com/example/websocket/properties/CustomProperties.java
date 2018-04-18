package com.example.websocket.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author wangyp
 */
@Data
@Configuration
@ConfigurationProperties( prefix = "custom" )
@PropertySource( "classpath:config/custom.properties" )
@Component
public class CustomProperties {

    private int randomInt;

    private String randomString;

    private String name;

    private String contextPath;
}
