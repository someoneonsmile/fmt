package com.example.basedemo.controller;

import com.example.basedemo.common.ResData;
import com.example.basedemo.common.SuperController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyp
 */
@Slf4j
@RestController
public class IndexController extends SuperController {

    @RequestMapping( {"/", "/index", "/main"} )
    public ResData hello() {
        return ResData.defaultSuccess();
    }
}
