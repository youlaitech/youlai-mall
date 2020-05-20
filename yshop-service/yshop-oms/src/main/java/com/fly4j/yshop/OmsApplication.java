package com.fly4j.yshop;

import com.fly4j.yshop.pms.feign.admin.PmsFeign;
import com.fly4j.yshop.pms.feign.app.PmsAppFeign;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author hxrui
 * @date 2020-04-13 14:26
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {PmsFeign.class, PmsAppFeign.class})
@MapperScan("com.fly4j.yshop.oms.mapper")
@EnableSwagger2
public class OmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(OmsApplication.class, args);
    }
}

