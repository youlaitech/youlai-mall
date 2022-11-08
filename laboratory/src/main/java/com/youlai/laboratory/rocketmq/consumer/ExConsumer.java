package com.youlai.laboratory.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author zc
 * @date 2022-10-27 00:50
 */
@Component
@RocketMQMessageListener(topic = "ex_topic", consumerGroup = "ex_group")
@Slf4j
public class ExConsumer implements RocketMQListener<MessageExt> {

    // 测试接收将参数topic定死，实际开发写入到配置文件

    @Override
    public void onMessage(MessageExt message) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("message对象:{}", message);
        log.info("接收消息:{}", msg);
    }

}
