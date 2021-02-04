package com.youlai.common.rabbitmq.demo.exchangeToExchange;

import com.rabbitmq.client.Channel;
import com.youlai.common.rabbitmq.demo.manager.ConnectionManager;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huawei
 * @desc RabbitMQ headers路由模型
 * @email huawei_code@163.com
 * @date 2021/1/30
 */
public class Recv {

    public static void main(String[] args) throws IOException, TimeoutException {
        Send.declareExchanges();
        Send.declareExchangesBindings();
        Recv.declareQueues();
        Recv.declareQueueBindings();

        Channel channel = ConnectionManager.getConnection().createChannel();

        channel.basicConsume("MobileQ", true, (consumerTag, message) -> {
            System.out.println("\n\n" + message.getEnvelope());
            System.out.println("MobileQ:" + new String(message.getBody()));
        }, consumerTag -> {
            System.out.println(consumerTag);
        });
        channel.basicConsume("ACQ", true, (consumerTag, message) -> {
            System.out.println("\n\n" + message.getEnvelope());
            System.out.println("ACQ:" + new String(message.getBody()));
        }, consumerTag -> {
            System.out.println(consumerTag);
        });
        channel.basicConsume("LightQ", true, (consumerTag, message) -> {
            System.out.println("\n\n" + message.getEnvelope());
            System.out.println("LightQ:" + new String(message.getBody()));
        }, consumerTag -> {
            System.out.println(consumerTag);
        });

        channel.basicConsume("LaptopQ", true, (consumerTag, message) -> {
            System.out.println("\n\n" + message.getEnvelope());
            System.out.println("LaptopQ:" + new String(message.getBody()));
        }, consumerTag -> {
            System.out.println(consumerTag);
        });
        channel.basicConsume("FanQ", true, (consumerTag, message) -> {
            System.out.println("\n\n" + message.getEnvelope());
            System.out.println("FanQ:" + new String(message.getBody()));
        }, consumerTag -> {
            System.out.println(consumerTag);
        });

    }

    //Declare the Queues
    public static void declareQueues() throws IOException, TimeoutException {
        //Create a channel - do not share the Channel instance
        Channel channel = ConnectionManager.getConnection().createChannel();
        //Create the Queues for linked-direct-exchange
        channel.queueDeclare("MobileQ", true, false, false, null);
        channel.queueDeclare("ACQ", true, false, false, null);
        channel.queueDeclare("LightQ", true, false, false, null);
        //Create the Queues for home-direct-exchange
        channel.queueDeclare("FanQ", true, false, false, null);
        channel.queueDeclare("LaptopQ", true, false, false, null);
        channel.close();
    }
    //Create the Bindings  between respective Queues and Exchanges
    public static void declareQueueBindings() throws IOException, TimeoutException {
        Channel channel = ConnectionManager.getConnection().createChannel();
        //Create bindings - (queue, exchange, routingKey)
        channel.queueBind("MobileQ", "linked-direct-exchange", "personalDevice");
        channel.queueBind("ACQ", "linked-direct-exchange", "homeAppliance");
        channel.queueBind("LightQ", "linked-direct-exchange", "homeAppliance");
        //Create the bindings - with home-direct-exchange
        channel.queueBind("FanQ", "home-direct-exchange", "homeAppliance");
        channel.queueBind("LaptopQ", "home-direct-exchange", "personalDevice");
        channel.close();
    }
}
