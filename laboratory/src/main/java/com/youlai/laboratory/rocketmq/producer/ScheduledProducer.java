package com.youlai.laboratory.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zc
 * @date 2022-10-27 01:00
 */
@Service
@Slf4j
public class ScheduledProducer {
    /**
     * 测试发送将参数topic定死，实际开发写入到配置文件
     */
    @Resource
    RocketMQTemplate rocketMQTemplate;

    public void scheduled() {
        String text = "延时消息"+ System.currentTimeMillis();
        log.info(text);

        // 设置延时等级2,这个消息将在5s之后发送
        // 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        Message<String> message = MessageBuilder.withPayload(text).build();
        rocketMQTemplate.syncSend("scheduled_topic", message, 1000, 2);

        log.info("已发送...");
    }
}
