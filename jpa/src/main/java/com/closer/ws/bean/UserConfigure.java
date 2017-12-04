package com.closer.ws.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "closer.site")
public class UserConfigure {

    private Integer tokenTimeout;
    private String tokenName;
    private String tokenServersName;
    private String tokenPrefix;
}
