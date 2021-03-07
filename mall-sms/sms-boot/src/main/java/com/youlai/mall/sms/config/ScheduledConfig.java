package com.youlai.mall.sms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author huawei
 * @desc 异步+定时 配置
 * @email huawei_code@163.com
 * @date 2021/3/5
 */
@EnableScheduling
@EnableAsync
@Configuration
public class ScheduledConfig {
}
