package com.example.basedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class BaseDemoApplication {

    public static void main( String[] args ) {
        SpringApplication.run(BaseDemoApplication.class, args);
    }
}
