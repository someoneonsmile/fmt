package com.closer.ws.controller.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class WsController {

    @MessageMapping("/send")
    @SendTo("/topic/wechat")
    public String send(String msg){
        System.out.println(msg);
        return msg;
    }

}
