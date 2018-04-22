package com.example.websocket.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiGreetingController {

    /** 支持跨域 */
    @CrossOrigin
    @RequestMapping("/insertNewNode")
    public void insertNewNode(){

    }

}
