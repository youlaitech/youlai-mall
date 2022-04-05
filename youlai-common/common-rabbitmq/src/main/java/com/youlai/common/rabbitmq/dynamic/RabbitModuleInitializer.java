package com.youlai.common.rabbitmq.dynamic;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RabbitMQ队列初始化器
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/4/4 15:16
 */
@Slf4j
public class RabbitModuleInitializer implements SmartInitializingSingleton {

    private AmqpAdmin amqpAdmin;

    private RabbitModuleProperties rabbitModuleProperties;

    public RabbitModuleInitializer(AmqpAdmin amqpAdmin, RabbitModuleProperties rabbitModuleProperties) {
        this.amqpAdmin = amqpAdmin;
        this.rabbitModuleProperties = rabbitModuleProperties;
    }

    @Override
    public void afterSingletonsInstantiated() {
        log.info("RabbitMQ 根据配置动态创建和绑定队列、交换机");
        declareRabbitModule();
    }

    /**
     * RabbitMQ 根据配置动态创建和绑定队列、交换机
     */
    private void declareRabbitModule() {
        List<RabbitModuleInfo> rabbitModuleInfos = rabbitModuleProperties.getModules();
        if (CollectionUtil.isEmpty(rabbitModuleInfos)) {
            return;
        }
        for (RabbitModuleInfo rabbitModuleInfo : rabbitModuleInfos) {
            configParamValidate(rabbitModuleInfo);

            // 队列
            Queue queue = convertQueue(rabbitModuleInfo.getQueue());
            // 交换机
            Exchange exchange = convertExchange(rabbitModuleInfo.getExchange());
            // 绑定关系
            String routingKey = rabbitModuleInfo.getRoutingKey();
            String queueName = rabbitModuleInfo.getQueue().getName();
            String exchangeName = rabbitModuleInfo.getExchange().getName();
            Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, routingKey, null);

            // 创建队列
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareExchange(exchange);
            amqpAdmin.declareBinding(binding);
        }
    }

    /**
     * RabbitMQ动态配置参数校验
     *
     * @param rabbitModuleInfo
     */
    public void configParamValidate(RabbitModuleInfo rabbitModuleInfo) {

        String routingKey = rabbitModuleInfo.getRoutingKey();

        Assert.isTrue(StrUtil.isNotBlank(routingKey), "RoutingKey 未配置");

        Assert.isTrue(rabbitModuleInfo.getExchange() != null, "routingKey:{}未配置exchange", routingKey);
        Assert.isTrue(StrUtil.isNotBlank(rabbitModuleInfo.getExchange().getName()), "routingKey:{}未配置exchange的name属性", routingKey);

        Assert.isTrue(rabbitModuleInfo.getQueue() != null, "routingKey:{}未配置queue", routingKey);
        Assert.isTrue(StrUtil.isNotBlank(rabbitModuleInfo.getQueue().getName()), "routingKey:{}未配置exchange的name属性", routingKey);

    }

    /**
     * 转换生成RabbitMQ队列
     *
     * @param queue
     * @return
     */
    public Queue convertQueue(RabbitModuleInfo.Queue queue) {
        Map<String, Object> arguments = queue.getArguments();

        // 转换ttl的类型为long
        if (arguments != null && arguments.containsKey("x-message-ttl")) {
            arguments.put("x-message-ttl", Convert.toLong(arguments.get("x-message-ttl")));
        }

        // 是否需要绑定死信队列
        String deadLetterExchange = queue.getDeadLetterExchange();
        String deadLetterRoutingKey = queue.getDeadLetterRoutingKey();
        if (StrUtil.isNotBlank(deadLetterExchange) && StrUtil.isNotBlank(deadLetterRoutingKey)) {

            if (arguments == null) {
                arguments = new HashMap<>(4);
            }
            arguments.put("x-dead-letter-exchange", deadLetterExchange);
            arguments.put("x-dead-letter-routing-key", deadLetterRoutingKey);

        }

        return new Queue(queue.getName(), queue.isDurable(), queue.isExclusive(), queue.isAutoDelete(), arguments);
    }


    /**
     * 转换生成RabbitMQ交换机
     *
     * @param exchangeInfo
     * @return
     */
    public Exchange convertExchange(RabbitModuleInfo.Exchange exchangeInfo) {

        AbstractExchange exchange = null;

        RabbitExchangeTypeEnum exchangeType = exchangeInfo.getType();

        String exchangeName = exchangeInfo.getName();
        boolean isDurable = exchangeInfo.isDurable();
        boolean isAutoDelete = exchangeInfo.isAutoDelete();

        Map<String, Object> arguments = exchangeInfo.getArguments();

        switch (exchangeType) {
            case DIRECT:// 直连交换机
                exchange = new DirectExchange(exchangeName, isDurable, isAutoDelete, arguments);
                break;
            case TOPIC: // 主题交换机
                exchange = new TopicExchange(exchangeName, isDurable, isAutoDelete, arguments);
                break;
            case FANOUT: //扇形交换机
                exchange = new FanoutExchange(exchangeName, isDurable, isAutoDelete, arguments);
                break;
            case HEADERS: // 头交换机
                exchange = new HeadersExchange(exchangeName, isDurable, isAutoDelete, arguments);
                break;
        }

        return exchange;

    }


}
