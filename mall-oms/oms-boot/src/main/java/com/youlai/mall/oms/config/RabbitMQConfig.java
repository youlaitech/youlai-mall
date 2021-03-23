package com.youlai.mall.oms.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huawei
 * @email huawei_code@163.com
 * @date 2021/1/17
 * @desc 业务描述
 * 1. 订单创建成功，发送消息到创建订单的路由
 * 2. 创建订单的路由转发消息给延时队列，延时队列的延时时间就是订单从创建到支付过程，允许的最大等待时间。延时队列不能有消费者（即消息不能被消费）
 * 3. 延时时间一到，消息被转入DLX（死信路由）
 * 4. 死信路由把死信消息转发给死信队列
 * 5. 订单系统监听死信队列，获取到死信消息后，执行关单解库存操作
 */
@Configuration
@Slf4j
@AllArgsConstructor
public class RabbitMQConfig {

    /**
     * 定义交换机，订单业务统一使用 order.exchange 交换机
     */
    @Bean
    public Exchange exchange() {
        return new TopicExchange("order.exchange", true, false);
    }

    /**
     * 延时队列（超时会通过交换机和路由key转发到死信队列），没有消费者
     */
    @Bean
    public Queue delayQueue() {
        // 延时队列的消息过期了，会自动触发消息的转发，根据routingKey发送到指定的exchange中，exchange路由到死信队列
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "order.exchange");
        args.put("x-dead-letter-routing-key", "order:close"); // 死信路由Key
        args.put("x-message-ttl", 60000); // 单位：毫秒，1分钟测试使用
        return new Queue("order.delay.queue", true, false, false, args);
    }


    /**
     * 延时队列绑定交换机，路由键order.create
     * 订单提交时会发送routingKey=order.create.order的消息至exchange，然后会被路由到上面的delayQueue延时队列，延时队列没有消费者，到期后会将消息转发
     */
    @Bean
    public Binding delayQueueBinding() {
        return new Binding("order.delay.queue", Binding.DestinationType.QUEUE,"order.exchange","order.create",null);
    }

    /**
     * 死信队列（普通队列）
     */
    @Bean
    public Queue closeOrderQueue() {
        return new Queue("order.close.queue", true, false, false);
    }

    /**
     * 死信队列绑定交换机
     * 其中死信路由的routingKey=order:close和延时队列的routingKey一致，延时队列过期时将消息发送给exchange，exchange再路由到死信队列
     */
    @Bean
    public Binding closeOrderQueueBinding() {
        return new Binding("order.close.queue", Binding.DestinationType.QUEUE,"order.exchange","order:close",null);
    }

}
