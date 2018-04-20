package com.example.websocket.controller;

import com.example.websocket.properties.CustomProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Slf4j
@Controller
public class GreetingController {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Resource
    private CustomProperties customProperties;

    @RequestMapping( {"/", "/index", "/main"} )
    @ResponseBody
    public void index() {
        System.out.println(customProperties.getName());
        System.out.println(customProperties.getRandomInt());
        System.out.println(customProperties.getRandomString());
        log.error("error");
        log.info("info");
        log.debug("debug");
    }


    @RequestMapping( {"/re"} )
    public String re() {
        return "reciver.html";
    }


    @RequestMapping( "/we" )
    public String we() {
        return "wechat.html";
    }


    @RequestMapping( "/send" )
    public String send() {
        return "sender.html";
    }


    @MessageMapping( "/wechat" )
    @SubscribeMapping
    @SendTo( "/topic/notice" )
    public String greeting( String value ) {
        System.out.println(value);
        // return value;
        // this.simpMessagingTemplate.convertAndSend("/topic/notice", value);
        return value;
    }


}
