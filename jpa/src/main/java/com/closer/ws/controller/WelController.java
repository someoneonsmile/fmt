package com.closer.ws.controller;

import com.closer.ws.common.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class WelController extends BaseController{

    /**
     * ws 连接测试页
     */
    @RequestMapping("/ws")
    public String ws(){
        return "ws";
    }

}
