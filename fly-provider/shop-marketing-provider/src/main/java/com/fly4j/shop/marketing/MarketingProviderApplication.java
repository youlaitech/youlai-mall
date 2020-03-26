package com.fly4j.shop.marketing;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@MapperScan("com.fly4j.shop.marketing.mapper")
public class MarketingProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketingProviderApplication.class, args);
    }

}
