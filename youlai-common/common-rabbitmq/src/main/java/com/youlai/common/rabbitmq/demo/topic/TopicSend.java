package com.youlai.common.rabbitmq.demo.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.youlai.common.rabbitmq.demo.manager.ConnectionManager;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huawei
 * @desc RabbitMQ 路由模型 - 通配符模式
 * 简单说明：
 * 1、路由模型需要指定交换机类型为 direct，交换机和队列之间通过路由键绑定，交换机只会把消息推送到符合路由键的队列中
 * @email huawei_code@163.com
 * @date 2021/1/30
 */
public class TopicSend {

    public static void main(String[] args) throws IOException, TimeoutException {
        TopicSend.declareExchange();
        try (Channel channel = ConnectionManager.getConnection().createChannel()) {

            String message = "Drink a lot of Water and stay Healthy!";
            channel.basicPublish("my-topic-exchange", "health.education", null, message.getBytes());
            message = "Learn something new everyday";
            channel.basicPublish("my-topic-exchange", "education", null, message.getBytes());
            message = "Stay fit in Mind and Body";
            channel.basicPublish("my-topic-exchange", "education.health", null, message.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void declareExchange() throws IOException, TimeoutException {
        Channel channel = ConnectionManager.getConnection().createChannel();
        //Declare my-fanout-exchange
        channel.exchangeDeclare("my-topic-exchange", BuiltinExchangeType.TOPIC, true);
        channel.close();
    }
}
