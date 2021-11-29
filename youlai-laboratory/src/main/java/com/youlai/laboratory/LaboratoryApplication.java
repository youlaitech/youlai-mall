package com.youlai.laboratory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @date 2021/11/29 0029 22:50
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LaboratoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(LaboratoryApplication.class,args);
    }
}
