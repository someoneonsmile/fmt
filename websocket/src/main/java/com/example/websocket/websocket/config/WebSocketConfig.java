package com.example.websocket.websocket.config;

import com.sun.istack.internal.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;


@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints( StompEndpointRegistry registry ) {
        registry.addEndpoint("/socket", "/another").withSockJS();
    }


    @Override
    public void configureMessageBroker( MessageBrokerRegistry registry ) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setUserDestinationPrefix("/user");
        registry.setApplicationDestinationPrefixes("/app");
    }


    @Override
    public void configureClientInboundChannel( ChannelRegistration registration ) {
        registration.taskExecutor().corePoolSize(4) // 核心线程数
            .maxPoolSize(8)  // 最大线程数
            .keepAliveSeconds(60) // 线程活动时间
            .queueCapacity(512); // 等待线程数
        super.configureClientInboundChannel(registration);
    }


    @Override
    public void configureWebSocketTransport( WebSocketTransportRegistration registration ) {

        // 消息大小限制
        registration.setMessageSizeLimit(2 * 1024);

        // 控制未发送消息的缓冲范围 (ms)
        registration.setSendTimeLimit(10 * 1000);

        // 设置缓存大小
        registration.setSendBufferSizeLimit(512 * 1024);

        registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {

            @Override
            public WebSocketHandler decorate( WebSocketHandler handler ) {
                return new WebSocketHandlerDecorator(handler) {

                    /**
                     * 建立 WebSocket 连接之后
                     */
                    @Override
                    public void afterConnectionEstablished( @Nullable final WebSocketSession session ) throws Exception {
                        log.info("afterConnectionEstablished");
                        if ( session == null ) {
                            throw new RuntimeException("session is null");
                        }
                        // Principal principal = session.getPrincipal();
                        // if ( principal == null ) {
                        //     throw new RuntimeException("principal is null");
                        // }
                        // String username = principal.getName();
                        // WebSocketSessionBean webSocketSessionBean = new WebSocketSessionBean(username, serverConfiguration.getServerId());
                        // log.info("上线: " + username);
                        // webSocketRepository.save(webSocketSessionBean);
                        super.afterConnectionEstablished(session);
                    }


                    /**
                     * 关闭 WebSocket 连接之后
                     */
                    @Override
                    public void afterConnectionClosed( @Nullable WebSocketSession session, @Nullable CloseStatus closeStatus ) throws Exception {
                        log.info("afterConnectionClosed");
                        if ( session == null || closeStatus == null ) {
                            throw new RuntimeException("session isnull or closeStatus is null");
                        }
                        // Principal principal = session.getPrincipal();
                        // if ( principal == null ) {
                        //     throw new RuntimeException("principal is null");
                        // }
                        // String username = principal.getName();
                        // log.info("下线: " + username);
                        // webSocketRepository.deleteByKeyAndValue(username, serverConfiguration.getServerId());
                        super.afterConnectionClosed(session, closeStatus);
                    }


                    /**
                     * 处理错误消息
                     */
                    @Override
                    public void handleTransportError( WebSocketSession session, Throwable exception ) throws Exception {
                        log.info("handleTransportError");
                        super.handleTransportError(session, exception);
                    }


                    /**
                     * 接收消息时的事件
                     */
                    @Override
                    public void handleMessage( WebSocketSession session, WebSocketMessage<?> message ) throws Exception {
                        log.info("handleMessage");
                        super.handleMessage(session, message);
                    }
                };
            }
        });
        super.configureWebSocketTransport(registration);
    }
}
