package com.fly4j.shop.marketing;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@MapperScan("com.fly4j.shop.marketing.mapper")
@EnableFeignClients(basePackages = "com.fly4j.shop.marketing.remote")
public class MarketingApplication {

    public static void main(String[] args) {

        SpringApplication.run(MarketingApplication.class, args);
    }

}
