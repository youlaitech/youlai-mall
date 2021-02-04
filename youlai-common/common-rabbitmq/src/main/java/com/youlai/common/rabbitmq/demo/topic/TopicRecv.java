package com.youlai.common.rabbitmq.demo.topic;

import com.rabbitmq.client.*;
import com.youlai.common.rabbitmq.demo.manager.ConnectionManager;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huawei
 * @desc RabbitMQ topic路由模型
 * @email huawei_code@163.com
 * @date 2021/1/30
 */
public class TopicRecv {

    public static void main(String[] args) throws IOException, TimeoutException {
        TopicSend.declareExchange();
        TopicRecv.declareQueues();
        TopicRecv.declareBindings();

        Channel channel = ConnectionManager.getConnection().createChannel();
        channel.basicConsume("HealthQueue", true, (consumerTag, message) -> {
            System.out.println("\n\n=========== Health Queue ==========");
            System.out.println(consumerTag);
            System.out.println("HealthQueue: " + new String(message.getBody()));
            System.out.println(message.getEnvelope());

        }, consumerTag -> {
            System.out.println(consumerTag);
        });

        channel.basicConsume("SportsQueue", true, (consumerTag, message) -> {
            System.out.println("\n\n=========== Sports Queue ==========");
            System.out.println(consumerTag);
            System.out.println("SportsQueue: " + new String(message.getBody()));
            System.out.println(message.getEnvelope());
        }, consumerTag -> {
            System.out.println(consumerTag);
        });

        channel.basicConsume("EducationQueue", true, (consumerTag, message) -> {
            System.out.println("\n\n=========== Education Queue ==========");
            System.out.println(consumerTag);
            System.out.println("EducationQueue: " + new String(message.getBody()));
            System.out.println(message.getEnvelope());
        }, consumerTag -> {
            System.out.println(consumerTag);
        });
    }

    public static void declareQueues() throws IOException, TimeoutException {
        //Create a channel - do no't share the Channel instance
        Channel channel = ConnectionManager.getConnection().createChannel();
        //queueDeclare  - (queueName, durable, exclusive, autoDelete, arguments)
        channel.queueDeclare("HealthQueue", true, false, false, null);
        channel.queueDeclare("SportsQueue", true, false, false, null);
        channel.queueDeclare("EducationQueue", true, false, false, null);

        channel.close();
    }

    public static void declareBindings() throws IOException, TimeoutException {
        Channel channel = ConnectionManager.getConnection().createChannel();
        //Create bindings - (queue, exchange, routingKey)
        channel.queueBind("HealthQueue", "my-topic-exchange", "health.*");
        channel.queueBind("SportsQueue", "my-topic-exchange", "#.sports.*");
        channel.queueBind("EducationQueue", "my-topic-exchange", "#.education");
        channel.close();
    }
}
