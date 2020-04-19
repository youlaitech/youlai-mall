package com.fly4j.yshop;

import com.fly4j.yshop.pms.feign.PmsFeign;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author fly2021【xianrui0365@163.com】
 * @date 2020-04-13 17:48
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {PmsFeign.class})
@MapperScan("com.fly4j.yshop.sms.mapper")
public class SmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }
}