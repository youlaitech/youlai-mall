package com.youlai.common.rabbitmq.demo.headers;

import com.rabbitmq.client.Channel;
import com.youlai.common.rabbitmq.demo.manager.ConnectionManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author huawei
 * @desc RabbitMQ headers路由模型
 * @email huawei_code@163.com
 * @date 2021/1/30
 */
public class HeadersRecv {

    public static void main(String[] args) throws IOException, TimeoutException {
        HeadersSend.declareExchange();
        HeadersRecv.declareQueues();
        HeadersRecv.declareBindings();

        Channel channel = ConnectionManager.getConnection().createChannel();

        channel.basicConsume("HealthQueue", true, ((consumerTag, message) -> {
            System.out.println("\n\n=========== Health Queue ==========");
            System.out.println(consumerTag);
            System.out.println("HealthQueue: " + new String(message.getBody()));
            System.out.println(message.getEnvelope());
        }), consumerTag -> {
            System.out.println(consumerTag);
        });
        channel.basicConsume("SportsQueue", true, ((consumerTag, message) -> {
            System.out.println("\n\n ============ Sports Queue ==========");
            System.out.println(consumerTag);
            System.out.println("SportsQueue: " + new String(message.getBody()));
            System.out.println(message.getEnvelope());
        }), consumerTag -> {
            System.out.println(consumerTag);
        });
        channel.basicConsume("EducationQueue", true, ((consumerTag, message) -> {
            System.out.println("\n\n ============ Education Queue ==========");
            System.out.println(consumerTag);
            System.out.println("EducationQueue: " + new String(message.getBody()));
            System.out.println(message.getEnvelope());
        }), consumerTag -> {
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
        //Create bindings - (queue, exchange, routingKey, headers) - routingKey != null
        Map<String, Object> healthArgs = new HashMap<>();
        healthArgs.put("x-match", "any"); //Match any of the header
        healthArgs.put("h1", "Header1");
        healthArgs.put("h2", "Header2");
        channel.queueBind("HealthQ", "my-header-exchange", "", healthArgs);

        Map<String, Object> sportsArgs = new HashMap<>();
        sportsArgs.put("x-match", "all"); //Match all of the header
        sportsArgs.put("h1", "Header1");
        sportsArgs.put("h2", "Header2");
        channel.queueBind("SportsQ", "my-header-exchange", "", sportsArgs);

        Map<String, Object> educationArgs = new HashMap<>();
        educationArgs.put("x-match", "any"); //Match any of the header
        educationArgs.put("h1", "Header1");
        educationArgs.put("h2", "Header2");
        channel.queueBind("EducationQ", "my-header-exchange", "", educationArgs);
        channel.close();

    }
}
