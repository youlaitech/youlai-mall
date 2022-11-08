package com.youlai.laboratory.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zc
 * @date 2022-10-27 01:00
 */
@Service
@Slf4j
public class SQLProducer {

    /**
     * 测试发送将参数topic定死，实际开发写入到配置文件
     */
    @Resource
    RocketMQTemplate rocketMQTemplate;

    /**
     * SQL92过滤消息
     */
    public void selector() {

        String text = "SQL92过滤消息" + System.currentTimeMillis();
        log.info(text);

        Message<String> message = MessageBuilder.withPayload(text).build();
        // 设置参数
        Map<String, Object> map = new HashMap<>(4);
        map.put("a", 2);
        map.put("b", 10);
        rocketMQTemplate.convertAndSend("sql_topic", message, map);
        log.info("已发送...");
    }
}

