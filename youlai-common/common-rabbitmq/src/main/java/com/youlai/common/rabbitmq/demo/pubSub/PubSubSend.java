package com.youlai.common.rabbitmq.demo.pubSub;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.youlai.common.rabbitmq.demo.manager.ConnectionManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author huawei
 * @desc RabbitMQ 发布订阅模型
 * 简单说明：
 * 1、之前工作模型中，我们虽然由多个消费者但是每个消息只能被一个消费者消费；在发布订阅模型中只要订阅了这个消息的消费者都可以接收到消息
 * 2、正规的 RabbitMQ 使用方式，消息生产者先将消息发送到 Exchange 交换机中，在根据一定的策略将消息投递到队列中，消息生产者甚至不用知道队列的存在
 * 3、Exchange 交换机需要做两件事：第一、接收来自生产者发送的消息；第二、将消息投递到队列中
 * 4、Exchange 交换机必须知道如何正确的将消息投递到队列中（Direct exchange、Fanout exchange、Topic exchange、Headers exchange）
 * @email huawei_code@163.com
 * @date 2021/1/30
 */
public class PubSubSend {

    public static void main(String[] args) {

        try (Channel channel = ConnectionManager.getConnection().createChannel()) {
            PubSubSend.declareExchange();
            String message = "Hello world !";
            channel.basicPublish("my-fanout-exchange", "", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("Send message ：" + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void declareExchange() throws IOException, TimeoutException {
        Channel channel = ConnectionManager.getConnection().createChannel();
        //Declare my-fanout-exchange
        channel.exchangeDeclare("my-fanout-exchange", BuiltinExchangeType.FANOUT, true);
        channel.close();
    }
}
