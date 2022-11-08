package com.youlai.laboratory.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author zc
 * @date 2022-10-27 00:54
 */
@Component
@RocketMQMessageListener(selectorExpression = "TAG-A||TAG-B", topic = "tag_topic", consumerGroup = "tag_group")
@Slf4j
public class TagConsumer implements RocketMQListener<String> {

    /**
     * 测试接收将参数topic定死，实际开发写入到配置文件
     *
     * @param message
     */
    @Override
    public void onMessage(String message) {
        log.info("标签过滤消息-接受到消息:" + message);
    }
}

