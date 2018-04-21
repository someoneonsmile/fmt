package com.example.websocket.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@ControllerAdvice
public class ResourceUrlProviderController {

    private final ResourceUrlProvider resourceUrlProvider;


    @Autowired
    public ResourceUrlProviderController( ResourceUrlProvider resourceUrlProvider ) {
        this.resourceUrlProvider = resourceUrlProvider;
    }


    @ModelAttribute( "urls" )
    public ResourceUrlProvider urls() {
        return this.resourceUrlProvider;
    }
}