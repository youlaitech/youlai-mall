package com.youlai.common.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;

/**
 * @author huawei
 * @desc
 * @email huawei_code@163.com
 * @date 2021/1/17
 */
@Configuration
@EnableTransactionManagement
@Slf4j
public class RabbitMqConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

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
     * 生产者投递消息后，如果Broker收到消息后，会给生产者一个ACK。生产者通过ACK，可以确认这条消息是否正常发送到Broker，这种方式是消息可靠性投递的核心
     * 步骤1：yaml文件中添加配置 spring.rabbitmq.publisher-confirm-type: correlated
     * 步骤2：编写代码
     */
    @PostConstruct
    public void setConfirmCallback() {

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {

            /**
             * @param correlationData 发送消息时指定的唯一关联数据（消息id）
             * @param ack             投递结果
             * @param cause           失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    log.info("消息投递到交换机成功：[correlationData={}]", correlationData);
                } else {
                    log.error("消息投递到交换机成功：[correlationData={}，原因={}]", correlationData, cause);
                }
            }
        });
    }

    /**
     *
     * 注意下面两项必须同时配置，可以尝试不配置第二项，通过测试能够发现当消息路由到Queue失败(比如路由件错误)时，returnCallback并未被回调。
     * # 开启阶段二(消息从E->Q)的确认回调    Exchange --> Queue  returnCallback
     * spring.rabbitmq.publisher-returns=true
     *
     * #为true,则交换机处理消息到路由失败，则会返回给生产者
     * spring.rabbitmq.template.mandatory=true
     */
    @PostConstruct
    public void setQueueCallback() {
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {

            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                log.error("路由到队列失败，[消息内容：{}，交换机：{}，路由件：{}，回复码：{}，回复文本：{}]",
                        returnedMessage.getMessage(), returnedMessage.getExchange(),
                        returnedMessage.getRoutingKey(), returnedMessage.getReplyCode(), returnedMessage.getReplyText());

            }
        });
    }


}
