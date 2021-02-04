package com.youlai.common.rabbitmq.demo.exchangeToExchange;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.youlai.common.rabbitmq.demo.manager.ConnectionManager;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huawei
 * @desc RabbitMQ headers模型
 * @email huawei_code@163.com
 * @date 2021/1/30
 */
public class Send {

    public static void main(String[] args) throws IOException, TimeoutException {
        Send.declareExchanges();
        Send.declareExchangesBindings();
        try (Channel channel = ConnectionManager.getConnection().createChannel()) {
            String message = "Direct message - Turn on the Home Appliances ";
            channel.basicPublish("home-direct-exchange", "homeAppliance", null, message.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Declare the exchanges
    public static void declareExchanges() throws IOException, TimeoutException {
        Channel channel = ConnectionManager.getConnection().createChannel();
        //Declare both the exchanges - linked-direct-exchange and home-direct-exchange.
        channel.exchangeDeclare("linked-direct-exchange", BuiltinExchangeType.DIRECT, true);
        channel.exchangeDeclare("home-direct-exchange", BuiltinExchangeType.DIRECT, true);
        channel.close();
    }

    //Create the Bindings between Exchanges.
    public static void declareExchangesBindings() throws IOException, TimeoutException {
        Channel channel = ConnectionManager.getConnection().createChannel();
        //                   (destination-exchange, source-exchange , routingKey
        channel.exchangeBind("linked-direct-exchange", "home-direct-exchange", "homeAppliance");
        channel.close();
    }
}
