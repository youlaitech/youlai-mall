package com.youlai.mall.oms;


import com.youlai.mall.pms.api.SkuFeignClient;
import com.youlai.mall.ums.api.MemberFeignClient;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = { MemberFeignClient.class, SkuFeignClient.class})
@EnableRabbit
public class OmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OmsApplication.class);
    }

}
