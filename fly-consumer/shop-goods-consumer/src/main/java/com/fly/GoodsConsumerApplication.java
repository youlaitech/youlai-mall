package com.fly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
* @Description: TODO
* @author: Mr.
* @create: 2020/3/5 16:07
**/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.fly.**.feign")
public class GoodsConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsConsumerApplication.class,args);
    }
}
