package com.youlai.common.rabbitmq.demo.work;

import cn.hutool.core.util.CharsetUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author huawei
 * @desc RabbitMQ 工作队列实战
 * <p>
 * 消息生产能力大于消费能力，增加多几个消费节点
 * 和简单队列类似，增加多个几个消费节点，处于竞争关系
 * 默认策略：round robin 轮训
 * @email huawei_code@163.com
 * @date 2021/1/29
 */
public class WorkerSend {

    private final static String QUEUE_NAME = "work_queue";

    public static void main(String[] args) {
        // 1、创建 rabbitmq 连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.56.10");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        // 2、创建 rabbitmq 连接和信道
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();) {

            /**
             * 参数：String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
             * queue: 队列名称
             * durable: 消息是否持久化
             * exclusive: 消息是否排他
             * autoDelete: 是否自动删除队列
             * arguments: 其他参数（例如：死信队列等信息）
             */
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            for (int i = 0; i < 10; i++) {
                /**
                 * 参数：String exchange, String routingKey, BasicProperties props, byte[] body
                 * exchange: 交换机名称：不写则是默认的交换机，那路由健需要和队列名称一样才可以被路由
                 * routingKey: 路由键
                 * props: 配置信息
                 * body: 发送的消息
                 */
                String message = "Hello world ！" + i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(CharsetUtil.UTF_8));
                System.out.println("--- Send Message: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
