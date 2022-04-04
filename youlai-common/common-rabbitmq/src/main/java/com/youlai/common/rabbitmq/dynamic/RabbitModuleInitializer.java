package com.youlai.common.rabbitmq.dynamic;

import cn.hutool.core.collection.CollectionUtil;
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
        log.info("初始化 RabbitMQ 队列、交换机和绑定关系");
        initRabbitModule();
    }

    /**
     * 初始化 RabbitMQ 队列、交换机和绑定关系
     */
    private void initRabbitModule() {
        List<RabbitModuleInfo> rabbitModuleInfos = rabbitModuleProperties.getModules();
        if (CollectionUtil.isEmpty(rabbitModuleInfos)) {
            return;
        }
        for (RabbitModuleInfo rabbitModuleInfo : rabbitModuleInfos) {
            configParamValidate(rabbitModuleInfo);

            // 队列
            Queue queue = convertToQueue(rabbitModuleInfo.getQueue());
            // 交换机
            Exchange exchange = convertToExchange(rabbitModuleInfo.getExchange());
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
    public Queue convertToQueue(RabbitModuleInfo.Queue queue) {
        Map<String, Object> arguments = queue.getArguments();

        // 是否需要绑定死信队列
        String deadExchangeName = queue.getDeadExchangeName();
        String deadRoutingKey = queue.getDeadRoutingKey();
        if (StrUtil.isNotBlank(deadExchangeName) && StrUtil.isNotBlank(deadRoutingKey)) {

            if (arguments != null) {
                arguments = new HashMap<>(4);
                arguments.put("x-dead-letter-exchange", deadExchangeName);
                arguments.put("x-dead-letter-routing-key", deadRoutingKey);
            }
        }

        return new Queue(queue.getName(), queue.isDurable(), queue.isExclusive(), queue.isAutoDelete(), arguments);
    }


    /**
     * 转换生成RabbitMQ交换机
     *
     * @param exchangeInfo
     * @return
     */
    public Exchange convertToExchange(RabbitModuleInfo.Exchange exchangeInfo) {

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

        exchange.setDelayed(exchangeInfo.isDelayed());
        return exchange;

    }


}
