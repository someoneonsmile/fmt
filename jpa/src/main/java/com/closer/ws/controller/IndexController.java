package com.closer.ws.controller;

import com.closer.ws.common.BaseController;
import com.closer.ws.common.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController extends BaseController {

    @Autowired
    private BaseEntity baseEntity;

    @RequestMapping("/index")
    public String index() throws InterruptedException {
        System.out.println("req: "+request.hashCode());
        System.out.println("reqToString: "+request.toString());
        System.out.println("res: "+response.hashCode());
        System.out.println("resToString: "+response.toString());
        System.out.println("session: "+session.hashCode());
        System.out.println("sessionToString: "+session.toString());
        System.out.println("sessionId: "+session.getId());
        System.out.println("entity: "+baseEntity.hashCode());
        System.out.println("entity.getId() --- : "+baseEntity.getId());
        System.out.println("===================================");
        Thread.sleep(3000);
        return "s";
    }
}
