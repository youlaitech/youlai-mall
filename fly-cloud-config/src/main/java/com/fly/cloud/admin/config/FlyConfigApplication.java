package com.fly.cloud.admin.config;

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
public class FlyConfigApplication {
    public static void main(String[] args) {
        log.debug("FlyConfigApplication start main");
        SpringApplication application=new SpringApplication(FlyConfigApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
