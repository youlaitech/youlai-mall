package com.youlai.mall.oms.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class RabbitMQTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void createOrderTest() {
        rabbitTemplate.convertAndSend("order.exchange", "order.create.routing.key", "4acd475a-c6aa-4d9a-a3a5-40da7472cbee");
    }
}
