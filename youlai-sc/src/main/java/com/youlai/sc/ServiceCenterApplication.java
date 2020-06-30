package com.youlai.sc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.youlai.sc.*.mapper")
@EnableSwagger2
public class ServiceCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCenterApplication.class);
    }
}
