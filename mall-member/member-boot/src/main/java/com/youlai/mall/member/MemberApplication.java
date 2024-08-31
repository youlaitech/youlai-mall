package com.youlai.mall.member;

import com.youlai.mall.product.api.SkuFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 会员服务启动类
 *
 * @author ray
 * @since 0.0.1
 */
@SpringBootApplication
@EnableDiscoveryClient()
@EnableFeignClients(basePackageClasses = {SkuFeignClient.class})
public class MemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }
}
