package com.youlai.mall.oms.service.impl;

import com.rabbitmq.client.Channel;
import com.youlai.mall.oms.pojo.entity.OrderEntity;
import com.youlai.mall.oms.service.OrderMqService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sun.plugin2.message.Message;

import static com.youlai.mall.oms.config.rabbitmq.RabbitMqConstants.ORDER_RELEASE_QUEUE;

/**
 * @author huawei
 * @desc 订单RabbitMQ相关业务
 * @email huawei_code@163.com
 * @date 2021/1/17
 */
@Service
@Slf4j
@AllArgsConstructor
public class OrderMqServiceImpl implements OrderMqService {

    @RabbitListener(queues = ORDER_RELEASE_QUEUE)
    @Override
    public void releaseOrder(OrderEntity orderEntity, Channel channel, Message message) {
        log.info("收到MQ推送释放订单信息，订单号:{}");
        // TODO 校验订单释放是未支付状态

        // 释放库存
    }
}
