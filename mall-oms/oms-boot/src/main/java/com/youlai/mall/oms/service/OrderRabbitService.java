package com.youlai.mall.oms.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

/**
 * @author huawei
 * @desc Order RabbitMQ 业务类
 * @email huawei_code@163.com
 * @date 2021/2/4
 */
public interface OrderRabbitService {

    /**
     * 订单超时释放
     *
     * @param orderSn
     * @param message
     * @param channel
     */
    void releaseOrder(String orderSn, Message message, Channel channel);
}
