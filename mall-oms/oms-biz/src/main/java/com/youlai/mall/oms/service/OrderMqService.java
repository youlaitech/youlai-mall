package com.youlai.mall.oms.service;

import com.rabbitmq.client.Channel;
import com.youlai.mall.oms.pojo.entity.OrderEntity;
import sun.plugin2.message.Message;

/**
 * @author huawei
 * @desc 订单RabbitMQ相关业务
 * @email huawei_code@163.com
 * @date 2021/1/17
 */
public interface OrderMqService {

    /**
     * 订单超时未支付自动关闭订单
     *
     * @param orderEntity 订单实体类
     * @param channel     channel通道
     * @param message     message消息
     */
    void releaseOrder(OrderEntity orderEntity, Channel channel, Message message);
}
