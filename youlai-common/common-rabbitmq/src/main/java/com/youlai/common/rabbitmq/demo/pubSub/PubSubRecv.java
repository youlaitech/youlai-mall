package com.youlai.common.rabbitmq.demo.pubSub;

import com.rabbitmq.client.Channel;
import com.youlai.common.rabbitmq.demo.manager.ConnectionManager;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huawei
 * @desc RabbitMQ 发布订阅模型实战
 * @email huawei_code@163.com
 * @date 2021/1/30
 */
public class PubSubRecv {


    public static void main(String[] args) throws IOException, TimeoutException {
        PubSubSend.declareExchange();
        PubSubRecv.declareQueues();
        PubSubRecv.declareBindings();

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
        channel.queueBind("WebClientQueue", "my-fanout-exchange", "");
        channel.queueBind("AppClientQueue", "my-fanout-exchange", "");
        channel.close();
    }
}
