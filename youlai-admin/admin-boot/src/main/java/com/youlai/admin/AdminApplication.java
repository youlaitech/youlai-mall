package com.youlai.admin;

import com.youlai.common.web.listener.LoggingListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationListener;

import java.util.Set;

@SpringBootApplication
@EnableDiscoveryClient
public class AdminApplication {
    public static void main(String[] args) {

        SpringApplication app =new SpringApplication(AdminApplication.class);
        LoggingListener loggingListener = new LoggingListener();
        app.addListeners(loggingListener);
        app.run(args);
    }
}
