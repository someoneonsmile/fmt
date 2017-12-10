package com.closer.ws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * webSocket configuration
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        // 客户端注册连接的端点
        stompEndpointRegistry.addEndpoint("/wsEndPoint").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 设置服务器广播消息的基础路径 ( destination 不以这里的为前缀时，消息接收不到 )
        registry.enableSimpleBroker("/topic", "/queue");
        // 客户端发送消息的前缀
        registry.setApplicationDestinationPrefixes("/app");
        // 订阅 SendToUser 消息的前缀
        // ( 客户端订阅 SendToUser 消息时，以 /user 为前缀，加上 destination--SendToUser的value )
        registry.setUserDestinationPrefix("/user");
    }
}
