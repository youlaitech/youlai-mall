package com.youlai.common.rabbitmq.config;

import com.youlai.common.rabbitmq.dynamic.RabbitModuleInitializer;
import com.youlai.common.rabbitmq.dynamic.RabbitModuleProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huawei
 * @email huawei_code@163.com
 * @date 2021/1/17
 */
@Configuration
@Slf4j
public class RabbitConfig {
    /**
     * 使用json序列化机制，进行消息转换
     *
     * @return
     */
    @Bean
    public MessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 动态创建队列、交换机初始化器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RabbitModuleInitializer rabbitModuleInitializer(AmqpAdmin amqpAdmin, RabbitModuleProperties rabbitModuleProperties) {
        return new RabbitModuleInitializer(amqpAdmin, rabbitModuleProperties);
    }

}
