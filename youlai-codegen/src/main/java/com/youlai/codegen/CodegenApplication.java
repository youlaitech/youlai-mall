package com.youlai.codegen;

import com.youlai.system.api.MenuFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 代码生成器启动类
 *
 * @author Ray
 * @since 4.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {MenuFeignClient.class})
public class CodegenApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodegenApplication.class, args);
    }
}
