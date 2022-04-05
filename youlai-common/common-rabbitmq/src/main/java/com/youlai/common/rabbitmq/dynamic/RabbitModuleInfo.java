package com.youlai.common.rabbitmq.dynamic;

import lombok.Data;

import java.util.Map;


/**
 * RabbitMQ 队列和交换机机绑定关系实体对象
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/4/4 0:20
 */
@Data
public class RabbitModuleInfo {

    /**
     * 路由Key
     */
    private String routingKey;

    /**
     * 队列信息
     */
    private Queue queue;

    /**
     * 交换机信息
     */
    private Exchange exchange;

    /**
     * 交换机信息类
     */
    @Data
    public static class Exchange {

        /**
         * 交换机类型
         */
        private RabbitExchangeTypeEnum type = RabbitExchangeTypeEnum.DIRECT; // 默认直连交换机

        /**
         * 交换机名称
         */
        private String name;

        /**
         * 是否持久化
         */
        private boolean durable = true; // 默认true持久化，重启消息不会丢失

        /**
         * 当所有队绑定列均不在使用时，是否自动删除交换机
         */
        private boolean autoDelete = false; // 默认false，不自动删除

        /**
         * 交换机其他参数
         */
        private Map<String, Object> arguments;

    }

    /**
     * 队列信息类
     */
    @Data
    public static class Queue {

        /**
         * 队列名称
         */
        private String name;

        /**
         * 是否持久化
         */
        private boolean durable = true; // 默认true持久化，重启消息不会丢失

        /**
         * 是否具有排他性
         */
        private boolean exclusive = false; // 默认false，可多个消费者消费同一个队列

        /**
         * 当消费者均断开连接，是否自动删除队列
         */
        private boolean autoDelete = false; // 默认false,不自动删除，避免消费者断开队列丢弃消息

        /**
         * 绑定死信队列的交换机名称
         */
        private String deadLetterExchange;

        /**
         * 绑定死信队列的路由key
         */
        private String deadLetterRoutingKey;


        private Map<String, Object> arguments;

    }

}
