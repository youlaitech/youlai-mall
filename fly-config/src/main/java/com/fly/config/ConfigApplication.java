package com.fly.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Created by XianRui on 2020-01-20 9:55
 **/

@Slf4j
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication application=new SpringApplication(ConfigApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
