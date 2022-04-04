package com.youlai.common.rabbitmq.dynamic;

/**
 * RabbitMQ 交换机类型枚举
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/4/4 10:34
 */
public enum RabbitExchangeTypeEnum {
    /**
     * 直连交换机
     * <p>
     * 根据routing-key精准匹配队列(最常使用)
     */
    DIRECT,

    /**
     * 主题交换机
     * <p>
     * 根据routing-key模糊匹配队列，*匹配任意一个字符，#匹配0个或多个字符
     */
    TOPIC,
    /**
     * 扇形交换机
     * <p>
     * 直接分发给所有绑定的队列，忽略routing-key,用于广播消息
     */
    FANOUT,
    /**
     * 头交换机
     * <p>
     * 类似直连交换机，不同于直连交换机的路由规则建立在头属性上而不是routing-key(使用较少)
     */
    HEADERS;
}
