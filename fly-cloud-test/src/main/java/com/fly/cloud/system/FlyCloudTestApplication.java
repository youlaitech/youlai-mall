package com.fly.cloud.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by XianRui on 2020-01-20 14:31
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class FlyCloudTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlyCloudTestApplication.class, args);
    }
}
