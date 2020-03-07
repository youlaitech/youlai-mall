package com.fly.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
* @Description: TODO
* @author: Mr.
* @create: 2020/3/4 23:02
**/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@MapperScan("com.fly.shop.dao")
public class GoodsProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsProviderApplication.class,args);
    }
}
