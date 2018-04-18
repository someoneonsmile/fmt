package com.example.websocket.controller;

import com.example.websocket.properties.CustomProperties;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class GreetingController {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Resource
    private CustomProperties customProperties;

    @RequestMapping( {"/", "/index", "/main"} )
    public String index() {
        System.out.println(customProperties.getName());
        System.out.println(customProperties.getRandomInt());
        System.out.println(customProperties.getRandomString());
        return "/reciver.html";
    }


    @RequestMapping( {"/re"} )
    public String re() {
        return "/reciver.html";
    }


    @RequestMapping( "/we" )
    public String we() {
        return "/wechat.html";
    }


    @RequestMapping( "/send" )
    public String send() {
        return "/sender.html";
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
