package com.youlai.mall.oms.config.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


/**
 * @author huawei
 * @desc rabbitmq 业务相关配置类
 * 订单相关业务统一使用 order-event-exchange 交换机
 * order.delay.queue 队列，用于存放需要延时多长时间处理的订单，不需要被消费
 * order.release.queue 队列，用于真正执行处理订单，需要被业务消费，如果订单还没有付款需要自动关闭订单并释放库存
 * <p>
 * order.create.order 建立交换机与 order.delay.queue 队列的绑定关系，目的是将新创建的订单放入延时队列中
 * order.release.order 建立交换机与 order.release.queue 队列的绑定关系，目的是将延时队列中到达延时时间的订单执行释放操作
 * <p>
 * 自动创建相关 交换机，队列，路由键
 * @email huawei_code@163.com
 * @date 2021/1/17
 */
@Configuration
@Slf4j
public class OmsRabbitConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 订单死信-延时队列
     *
     * @return
     */
    @Bean
    public Queue orderDelayQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", OmsRabbitConstants.ORDER_EVENT_EXCHANGE);
        args.put("x-dead-letter-routing-key", OmsRabbitConstants.ORDER_RELEASE_ORDER_KEY);
        args.put("x-message-ttl", 60000);
        Queue queue = new Queue(OmsRabbitConstants.ORDER_DELAY_QUEUE, true, false, false, args);

        return queue;

    }

    /**
     * 释放订单队列
     * 订单系统从该队列中取出订单order，判断是否超时未支付
     *
     * @return
     */
    @Bean
    public Queue orderReleaseQueue() {
        return new Queue(OmsRabbitConstants.ORDER_RELEASE_QUEUE, true, false, false);
    }

    /**
     * Topic类型 订单交换机
     * @return
     */
    @Bean
    public Exchange orderEventExchange() {
        return new TopicExchange(OmsRabbitConstants.ORDER_EVENT_EXCHANGE, true, false);
    }

    /**
     * 建立 order.delay.queue 队列与交换机绑定
      * @return
     */
    @Bean
    public Binding orderCreateOrderBinding() {
        //String destination, DestinationType destinationType, String exchange, String routingKey,
        //			@Nullable Map<String, Object> arguments
        return new Binding(OmsRabbitConstants.ORDER_DELAY_QUEUE, Binding.DestinationType.QUEUE, OmsRabbitConstants.ORDER_EVENT_EXCHANGE, OmsRabbitConstants.ORDER_CREATE_ORDER_KEY, null);
    }

    /**
     * 建立 order.release.queue 与交换机绑定
     * @return
     */
    @Bean
    public Binding orderReleaseOrderBinding() {
        return new Binding(OmsRabbitConstants.ORDER_RELEASE_QUEUE, Binding.DestinationType.QUEUE, OmsRabbitConstants.ORDER_EVENT_EXCHANGE, OmsRabbitConstants.ORDER_RELEASE_ORDER_KEY, null);

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

                //TODO 根据ACK状态做对应的消息更新操作
            }
        });
    }

    /**
     *
     * 注意下面两项必须同时配置
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
