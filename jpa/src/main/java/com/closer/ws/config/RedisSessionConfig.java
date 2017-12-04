package com.closer.ws.config;

import com.closer.ws.bean.SpringSessionCookieProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
@EnableConfigurationProperties({SpringSessionCookieProperties.class})
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3 * 24 * 60 * 60, redisNamespace = "closer", redisFlushMode = RedisFlushMode.IMMEDIATE)
public class RedisSessionConfig {

    private final SpringSessionCookieProperties springSessionCookieProperties;

    public RedisSessionConfig(@Autowired SpringSessionCookieProperties springSessionCookieProperties) {
        this.springSessionCookieProperties = springSessionCookieProperties;
    }

    @Bean
    public CookieHttpSessionStrategy cookieHttpSessionStrategy() {
        CookieHttpSessionStrategy sessionStrategy = new CookieHttpSessionStrategy();
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookieMaxAge(springSessionCookieProperties.getMaxAge());
        cookieSerializer.setCookiePath(springSessionCookieProperties.getPath());
        cookieSerializer.setCookieName(springSessionCookieProperties.getName());
        sessionStrategy.setCookieSerializer(cookieSerializer);
        return sessionStrategy;
    }
}
