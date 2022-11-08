package com.youlai.laboratory.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zc
 * @date 2022-10-27 00:59
 */
@Service
@Slf4j
public class ReplyProducer {
    /**
     * 测试发送将参数topic定死，实际开发写入到配置文件
     */
    @Resource
    RocketMQTemplate rocketMQTemplate;

    public void reply() {

        // 如果消费者没有回馈消息，则不会发送下一条消息
        for (int i = 1; i <= 10; i++) {
            String text = "回馈消息" + "--" + i;
            log.info("发送" + text);
            Object obj = rocketMQTemplate.sendAndReceive("reply_topic", text, String.class);
            log.info("消费者返回的消息：" + obj);
        }
    }
}

