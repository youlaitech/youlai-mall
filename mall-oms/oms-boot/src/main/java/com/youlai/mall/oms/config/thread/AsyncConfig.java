//package com.youlai.mall.oms.config.thread;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.AsyncTaskExecutor;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.concurrent.ThreadPoolExecutor;
//
///**
// * @author huawei
// * @desc 异步任务线程配置
// * @email huawei_code@163.com
// * @date 2021/1/16
// */
//@Configuration
//@Slf4j
//public class AsyncConfig {
//
//    @Bean("asyncTaskExecutor")
//    public AsyncTaskExecutor asyncTaskExecutor(ThreadPoolProperties properties) {
//        log.info("loading asyncTaskExecutor ...");
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        //配置核心线程数
//        executor.setCorePoolSize(properties.getCorePoolSize());
//        //配置最大线程数
//        executor.setMaxPoolSize(properties.getMaxPoolSize());
//        //配置队列大小
//        executor.setQueueCapacity(properties.getQueueCapacity());
//        executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
//        //配置线程池中的线程的名称前缀
//        executor.setThreadNamePrefix("async-service-");
//
//        // 设置拒绝策略：当pool已经达到max size的时候，如何处理新任务
//        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        //执行初始化
//        executor.initialize();
//        return executor;
//    }
//}
