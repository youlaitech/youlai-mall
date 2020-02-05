package com.fly.cloud.admin.biz;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages = {"com.fly.cloud.admin.biz"})
@MapperScan(basePackages = "com.fly.cloud.admin.biz.mapper")
@EnableTransactionManagement
@EnableDiscoveryClient
public class AdminBizApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminBizApplication.class,args);
    }
}
