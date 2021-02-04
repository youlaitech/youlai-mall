package com.youlai.common.rabbitmq.demo.routing;

import cn.hutool.core.util.CharsetUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.youlai.common.rabbitmq.demo.manager.ConnectionManager;

/**
 * @author huawei
 * @desc RabbitMQ 路由模型
 * 简单说明：
 * 1、路由模型需要指定交换机类型为 direct，交换机和队列之间通过路由键绑定，交换机只会把消息推送到符合路由键的队列中
 * @email huawei_code@163.com
 * @date 2021/1/30
 */
public class RoutingSend {

    private final static String EXCHANGE_NAME = "my_exchange_direct";

    public static void main(String[] args) {
        // 1、创建 rabbitmq 连接和信道
        try (Channel channel = ConnectionManager.getConnection().createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true, false, false, null);
            String message = "Direct exchange，这条消息路由键是：info";
            channel.basicPublish(EXCHANGE_NAME, "info", null, message.getBytes(CharsetUtil.UTF_8));

            message = "Direct exchange，这条消息路由键是：error";
            channel.basicPublish(EXCHANGE_NAME, "error", null, message.getBytes(CharsetUtil.UTF_8));

            message = "Direct exchange，这条消息路由键是：warning";
            channel.basicPublish(EXCHANGE_NAME, "warning", null, message.getBytes(CharsetUtil.UTF_8));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
