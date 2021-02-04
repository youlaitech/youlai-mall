package com.youlai.common.rabbitmq.demo.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huawei
 * @desc RabbitMQ 工作队列实战
 * RabbitMQ 工作队列模型，是创建多个消费者共同消费消息，每个消息只可以被一个消费者处理
 * 默认是轮询策略
 *
 * 简单队列模型和工作队列模型对比：
 * 区别：
 *  1、简单队列模型：是一个消费者和一个生产者
 *  2、工作队列模型：由一个生产者生产消息，多个消费者共同消费消息，但是每个消息只可以被一个消费者处理
 *
 * 共同点：两种队列模型：都是直接将消息发送到Queue队列中，并没有Exchange交换机参与
 * @email huawei_code@163.com
 * @date 2021/1/30
 */
public class WorkerRecv2 {

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

            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");

            try {
                doWork(message);
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };

        // 5、关闭自动确认消息
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> { });
    }

    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
