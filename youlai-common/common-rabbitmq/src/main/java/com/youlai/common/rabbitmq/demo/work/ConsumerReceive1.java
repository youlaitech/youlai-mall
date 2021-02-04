package com.youlai.common.rabbitmq.demo.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author huawei
 * @desc RabbitMQ 工作队列实战
 * RabbitMQ 工作队列模型，是创建多个消费者共同消费消息，每个消息只可以被一个消费者处理
 * 默认是轮询策略：使用轮询无法根据消费者消费速度合理分配消费数量，而是平均分配
 * 需要限制消费者每次消费消息的数量，每次消费完了才可以进行下一次消费
 * @email huawei_code@163.com
 * @date 2021/1/30
 */
public class ConsumerReceive1 {

    private final static String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        // 1、创建 rabbitmq 连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.56.10");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        // 2、创建连接，建立信道
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 3、指定要消费的队列，注意这里的配置必须与消息发送方配置的一直，否则无法消费
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // 模拟轮询策略，请将这部分注释
        // 4、限制消费者每次消费消息数量，每次消息处理完成后才能消费下一条消息
        int fetchCount = 1;
        channel.basicQos(fetchCount);

        // 4、创建回调消费消息，并手动确认收到消息
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            //模拟消费缓慢
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("--- Consumer 1 Received Message: '" + message + "'");

            //手工确认消息消费，不是多条确认
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        //关闭自动确认消息
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> { });


    }
}
