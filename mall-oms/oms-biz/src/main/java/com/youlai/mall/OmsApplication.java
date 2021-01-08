package com.youlai.mall;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableSwagger2
@EnableFeignClients
@MapperScan({"com.youlai.mall.oms.mapper","com.youlai.mall.oms.dao"})
public class OmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OmsApplication.class);
    }

}
