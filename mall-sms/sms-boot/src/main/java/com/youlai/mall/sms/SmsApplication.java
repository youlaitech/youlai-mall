package com.youlai.mall.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 优惠营销系统
 * 秒杀功能开发
 * 1、管理员端开发秒杀活动管理界面（创建秒杀活动场次，建立秒杀活动场次与商品关联）
 * 2、秒杀预热。采用 异步+定时 将秒杀数据提前同步到redis中
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class SmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }
}
