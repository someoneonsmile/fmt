package com.closer.ws.controller.ws;

import com.closer.ws.common.ResData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
public class WsController {

    @MessageMapping("/send")
    @SendTo("/topic/wechat")
    public String send(ResData msg){
        System.out.println(msg.getMsg());
        return msg.getMsg();
    }

    @MessageMapping("/sendToUser")
    @SendToUser("/queue/wechat")
    public String sendToUser(ResData res){
        System.out.println(res.getMsg());
        return res.getMsg();
    }

}
