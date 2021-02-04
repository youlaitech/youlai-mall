package com.youlai.common.rabbitmq.demo.simple;

import com.rabbitmq.client.*;
import com.youlai.common.rabbitmq.demo.manager.ConnectionManager;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huawei
 * @desc RabbitMQ 简单队列实战
 * @email huawei_code@163.com
 * @date 2021/1/30
 */
public class SimpleRecv {

    public static void main(String[] args) throws IOException, TimeoutException {

        // 2、创建连接，建立信道
        Channel channel = ConnectionManager.getConnection().createChannel();

        // 3、指定要消费的队列，注意这里的配置必须与消息发送方配置的一直，否则无法消费
        channel.queueDeclare("hello", true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // 4、接收处理消息并自动确认
        channel.basicConsume("hello", true, (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        }, consumerTag -> {
            System.out.println(consumerTag);
        });
    }
}
