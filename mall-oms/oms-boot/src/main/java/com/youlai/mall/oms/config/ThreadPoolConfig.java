package com.youlai.mall.oms.config;

import com.youlai.common.factory.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 自定义订单线程池
 *
 * @author haoxr
 * @date 2022/2/13
 */
@Configuration
@Slf4j
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        int cpuCoreSize = Runtime.getRuntime().availableProcessors();
        log.info("当前CPU核心数:{}", cpuCoreSize);

        /**
         * 计算密集型: 核心线程数=CPU核心 +1   √
         * I/O密集型: 核心线程数=2*CPU核心 +1
         */
        Integer corePoolSize = cpuCoreSize + 1;

        return new ThreadPoolExecutor(
                corePoolSize,
                2 * corePoolSize,
                30,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000),
                new NamedThreadFactory("order") // 订单线程
        );
    }

}
