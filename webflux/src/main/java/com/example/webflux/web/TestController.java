package com.example.webflux.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author wangyp
 */
@Controller
public class TestController {

    @ResponseBody
    @RequestMapping( {"/"} )
    public Optional<String> index() {

        return Optional.of("ind ex");
    }


    @ResponseBody
    @RequestMapping( {"/hello"} )
    public Mono<String> hello() {

        return Mono.just("hello");
    }
}
