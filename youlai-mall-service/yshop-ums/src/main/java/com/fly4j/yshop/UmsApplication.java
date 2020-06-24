package com.fly4j.yshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author hxrui
 * @date 2020-04-13 17:47
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.fly4j.yshop.ums.mapper")
@EnableSwagger2
public class UmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(UmsApplication.class, args);
    }
}