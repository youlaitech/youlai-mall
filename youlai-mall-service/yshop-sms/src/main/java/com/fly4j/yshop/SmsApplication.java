package com.fly4j.yshop;

import com.fly4j.yshop.pms.feign.admin.PmsFeign;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author hxrui
 * @date 2020-04-13 17:48
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {PmsFeign.class})
@MapperScan("com.fly4j.yshop.sms.mapper")
@EnableSwagger2
public class SmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }
}