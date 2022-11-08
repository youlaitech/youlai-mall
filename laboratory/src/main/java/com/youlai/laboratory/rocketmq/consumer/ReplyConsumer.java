package com.youlai.laboratory.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Component;

/**
 * @author zc
 * @date 2022-10-27 00:53
 */
@Component
@RocketMQMessageListener(topic = "reply_topic", consumerGroup = "reply_group")
@Slf4j
public class ReplyConsumer implements RocketMQReplyListener<String, byte[]> {

    @Override
    public byte[] onMessage(String message) {
        log.info("接受到消息:" + message);

        // 返回消息到生成者
        return "返回消息到生产者".getBytes();
    }

}
