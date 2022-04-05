package com.youlai.common.rabbitmq.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单超时关单延时队列
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/2/4 23:21
 */

@Deprecated
@Slf4j
public class OrderCloseQueue {

    /**
     * 定义交换机
     */
    @Bean
    public Exchange orderExchange() {
        return new DirectExchange("order.exchange", true, false);
    }

    /**
     * 延时队列
     */
    @Bean
    public Queue orderDelayQueue() {
        log.info("延时队列(order.delay.queue)创建");
        // 延时队列的消息过期了，会自动触发消息的转发，根据routingKey发送到指定的exchange中，exchange路由到死信队列
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "order.exchange");
        args.put("x-dead-letter-routing-key", "order.close.routing.key"); // 死信路由Key
        args.put("x-message-ttl", 60 * 1000L); // 单位：毫秒，1分钟测试使用
        return new Queue("order.delay.queue", true, false, false, args);
    }


    /**
     * 延时队列绑定交换机
     */
    @Bean
    public Binding orderDelayQueueBinding() {
        return new Binding("order.delay.queue", Binding.DestinationType.QUEUE, "order.exchange", "order.create.routing.key", null);
    }


    /**
     * 死信队列
     */
    @Bean
    public Queue orderCloseQueue() {
        log.info("死信队列(order.close.queue)创建");
        return new Queue("order.close.queue", true, false, false);
    }

    /**
     * 死信队列绑定交换机
     */
    @Bean
    public Binding orderCloseQueueBinding() {
        return new Binding("order.close.queue", Binding.DestinationType.QUEUE, "order.exchange", "order.close.routing.key", null);
    }

}
