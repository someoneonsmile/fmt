package com.example.websocket.stomp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

/**
 * @author wangyp
 */
@Slf4j
@Component
public class StompEventListener {

    private final SimpMessagingTemplate template;


    @Autowired
    public StompEventListener( SimpMessagingTemplate template ) {
        this.template = template;
    }


    @EventListener
    public void handlerSubscribeEvent( SessionSubscribeEvent event ) {
        log.info("handlerSubscribeEvent");
        // template.convertAndSendToUser(event.getUser().getName(), "/queue/notify", "GREETINGS");
    }


    @EventListener
    public void handlerSessionConnectEvent( SessionConnectEvent event ) {
        log.info("handlerSessionConnectEvent");
        // template.convertAndSendToUser(event.getUser().getName(), "/queue/notify", "GREETINGS");
    }


    @EventListener
    public void handlerSessionConnectedEvent( SessionConnectedEvent event ) {
        log.info("handlerSessionConnectedEvent");
        // template.convertAndSendToUser(event.getUser().getName(), "/queue/notify", "GREETINGS");
    }


    @EventListener
    public void handlerSessionDisconnectEvent( SessionDisconnectEvent event ) {
        log.info("handlerSessionDisconnectEvent");
        // template.convertAndSendToUser(event.getUser().getName(), "/queue/notify", "GREETINGS");
    }


    @EventListener
    public void handlerSessionUnsubscribeEvent( SessionUnsubscribeEvent event ) {
        log.info("handlerSessionUnsubscribeEvent");
        // template.convertAndSendToUser(event.getUser().getName(), "/queue/notify", "GREETINGS");
    }
}
