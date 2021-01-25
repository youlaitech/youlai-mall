package com.youlai.common.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
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
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 为容器创建号rabbitTemplate注册confirmCallback
     * 消息由生产者投递到Broker/Exchange回调
     */
    @PostConstruct
    public void setExchangeCallback() {

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
     * 注意下面两项必须同时配置，可以尝试不配置第二项，通过测试能够发现当消息路由到Queue失败(比如路由件错误)时，returnCallback并未被回调。
     * # 开启阶段二(消息从E->Q)的确认回调    Exchange --> Queue  returnCallback
     * spring.rabbitmq.publisher-returns=true
     * # 官方文档说此时这一项必须设置为true
     * # 实际上这一项的作用是：消息【未成功到达】队列时，能监听到到路由不可达的消息，以异步方式优先调用我们自己设置的returnCallback，默认情况下，这个消息会被直接丢弃，无法监听到
     * spring.rabbitmq.template.mandatory=true
     */
    @PostConstruct
    public void setQueueCallback() {
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 这个方法的参数并没有像 confirmCallback 那样提供boolean类型的ack，因此这个回调只能在【失败】情况下触发
             * @param message 发送消息
             * @param replyCode 回复错误码
             * @param replyText 回复错误内容
             * @param exchange 发送消息时指定的交换机
             * @param routingKey 发送消息时使用的路由件
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.error("路由到队列失败，[消息内容：{}，交换机：{}，路由件：{}，回复码：{}，回复文本：{}]", message, exchange, routingKey, replyCode, replyText);
            }
        });
    }


}
