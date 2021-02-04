package com.youlai.common.rabbitmq.demo.simple;

import com.rabbitmq.client.Channel;
import com.youlai.common.rabbitmq.demo.manager.ConnectionManager;

import java.nio.charset.StandardCharsets;

/**
 * @author huawei
 * @desc RabbitMQ 简单队列实战
 * @email huawei_code@163.com
 * @date 2021/1/29
 */
public class SimpleSend {

    public static void main(String[] args) {

        try (Channel channel = ConnectionManager.getConnection().createChannel();){
            /**
             * queue: 队列名称
             * durable: 消息是否持久化
             * exclusive: 消息是否排他
             * autoDelete: 是否自动删除队列
             * arguments: 其他参数（例如：死信队列等信息）
             */
            channel.queueDeclare("hello", true, false, false, null);
            String message = "hello!";

            /**
             * 参数：String exchange, String routingKey, BasicProperties props, byte[] body
             * exchange: 交换机名称：不写则是默认的交换机，那路由健需要和队列名称一样才可以被路由
             * routingKey: 路由键
             * props: 配置信息
             * body: 发送的消息
             */
            channel.basicPublish("", "hello", null, message.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
