package com.youlai.lab;

import com.youlai.mall.oms.api.OrderFeignClient;
import com.youlai.mall.pms.api.SkuFeignClient;
import com.youlai.mall.ums.api.MemberFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 实验室启动类
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @date 2021/11/29 0029 22:50
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {OrderFeignClient.class, SkuFeignClient.class, MemberFeignClient.class})
public class LabApplication {
    public static void main(String[] args) {
        SpringApplication.run(LabApplication.class, args);
    }
}
