package com.fly.cloud.admin.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by XianRui on 2020-01-18 22:19
 **/
@SpringBootApplication
@EnableEurekaServer
public class FlyCloudEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlyCloudEurekaApplication.class, args);
    }
}
