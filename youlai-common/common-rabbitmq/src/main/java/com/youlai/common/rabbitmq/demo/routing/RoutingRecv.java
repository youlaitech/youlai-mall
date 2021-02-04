package com.youlai.common.rabbitmq.demo.routing;

import com.rabbitmq.client.*;
import com.youlai.common.rabbitmq.demo.manager.ConnectionManager;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huawei
 * @desc RabbitMQ 路由模型实战
 * @email huawei_code@163.com
 * @date 2021/1/30
 */
public class RoutingRecv {

    public static void main(String[] args) throws IOException, TimeoutException {

        // 1、创建队列，并建立队列与路由器绑定关系
        RoutingRecv.declareQueues();
        RoutingRecv.declareBindings();

        // 2、消费消息查看消费结果
        Channel channel = ConnectionManager.getConnection().createChannel();
        channel.basicConsume("WebClientQueue", true, (consumerTag, message) -> {
            System.out.println(consumerTag);
            System.out.println("WebClientQueue:" + new String(message.getBody()));
        }, consumerTag -> {
            System.out.println(consumerTag);
        });

        channel.basicConsume("AppClientQueue", true, (consumerTag, message) -> {
            System.out.println(consumerTag);
            System.out.println("AppClientQueue:" + new String(message.getBody()));
        }, consumerTag -> {
            System.out.println(consumerTag);
        });

    }

    public static void declareQueues() throws IOException, TimeoutException {
        //Create a channel - do no't share the Channel instance
        Channel channel = ConnectionManager.getConnection().createChannel();
        //queueDeclare  - (queueName, durable, exclusive, autoDelete, arguments)
        channel.queueDeclare("WebClientQueue", true, false, false, null);
        channel.queueDeclare("AppClientQueue", true, false, false, null);
        channel.close();
    }

    public static void declareBindings() throws IOException, TimeoutException {
        Channel channel = ConnectionManager.getConnection().createChannel();
        //Create bindings - (queue, exchange, routingKey)
        channel.queueBind("WebClientQueue", "my_exchange_direct", "info");
        channel.queueBind("WebClientQueue", "my_exchange_direct", "error");
        channel.queueBind("WebClientQueue", "my_exchange_direct", "warning");
        channel.queueBind("AppClientQueue", "my_exchange_direct", "error");
        channel.close();
    }


}
