package com.fly.cloud.authentication.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.fly.cloud.authentication.server.dao")
public class Oauth2AuthenticationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2AuthenticationServerApplication.class,args);
    }
}
