package com.fly4j.yshop.oms.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description: 消息队列测试
 * @author: Mr.
 * @create: 2020-04-19 01:57
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTests {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void contextLoads() {

        this.amqpTemplate.convertAndSend("ORDER-EXCHANGE", "order.create", "hello world!");
    }
}
