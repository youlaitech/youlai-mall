package com.fly4j.yshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.fly4j.yshop.pms.feign")
@MapperScan("com.fly4j.yshop.pms.mapper")
public class PmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(PmsApplication.class, args);
    }
}
