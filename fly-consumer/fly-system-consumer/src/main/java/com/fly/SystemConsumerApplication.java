package com.fly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by XianRui on 2020-03-02 18:38
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class SystemConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemConsumerApplication.class,args);
    }

}
