package com.example.file;

import com.example.file.resolver.FileArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class FileApplication implements WebMvcConfigurer {

    public static void main( String[] args ) {
        SpringApplication.run(FileApplication.class, args);
    }

    @Override
    public void addArgumentResolvers( List<HandlerMethodArgumentResolver> argumentResolvers ) {
        argumentResolvers.add(new FileArgumentResolver());
    }
}
