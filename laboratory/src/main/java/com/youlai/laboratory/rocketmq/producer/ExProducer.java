package com.youlai.laboratory.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zc
 * @date 2022-10-27 00:58
 */
@Service
@Slf4j
public class ExProducer {
    /**
     * 测试发送将参数topic定死，实际开发写入到配置文件
     */
    @Resource
    RocketMQTemplate rocketMQTemplate;

    public void ex() {
        String text = "消息额外属性测试" + System.currentTimeMillis();
        log.info(text);

        rocketMQTemplate.syncSend("ex_topic", text);

        log.info("已发送...");

    }
}

