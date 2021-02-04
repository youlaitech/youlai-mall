package com.youlai.common.rabbitmq.demo.headers;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.youlai.common.rabbitmq.demo.manager.ConnectionManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author huawei
 * @desc RabbitMQ headers模型
 * @email huawei_code@163.com
 * @date 2021/1/30
 */
public class HeadersSend {

    public static void main(String[] args) throws IOException, TimeoutException {
        HeadersSend.declareExchange();
        try (Channel channel = ConnectionManager.getConnection().createChannel()) {

            String message = "Header Exchange example 1";
            Map<String, Object> headerMap = new HashMap<>();
            headerMap.put("h1", "Header1");
            headerMap.put("h3", "Header3");
            AMQP.BasicProperties properties = new AMQP.BasicProperties()
                    .builder().headers(headerMap).build();
            channel.basicPublish("my-header-exchange", "", properties, message.getBytes());

            message = "Header Exchange example 2";
            headerMap.put("h2", "Header2");
            properties = new AMQP.BasicProperties()
                    .builder().headers(headerMap).build();
            channel.basicPublish("my-header-exchange", "", properties, message.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void declareExchange() throws IOException, TimeoutException {
        Channel channel = ConnectionManager.getConnection().createChannel();
        //Declare my-fanout-exchange
        channel.exchangeDeclare("my-header-exchange", BuiltinExchangeType.HEADERS, true);
        channel.close();
    }
}
