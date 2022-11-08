package com.youlai.laboratory.rocketmq.producer.tx;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author zc
 * @date 2022-10-27 00:56
 */
@Service
@Slf4j
public class TxProducer {
    /**
     * 测试发送将参数topic定死，实际开发写入到配置文件
     * 一个RocketMQTemplate只能注册一个事务监听器，
     * 如果存在多个事务监听器监听不同的`Producer`，
     * 需要通过注解`@ExtRocketMQTemplateConfiguration`定义不同的RocketMQTemplate
     */
    @Resource(name = "txRocketMQTemplate")
    RocketMQTemplate rocketMQTemplate;

    public void tx() {
        String text = "消息事务发送" + System.currentTimeMillis();
        log.info(text);
        UUID transactionId = UUID.randomUUID();
        log.info("事务ID：" + transactionId);
        Message<String> message = MessageBuilder.withPayload(text)
                // 设置事务Id
                .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                .build();
        rocketMQTemplate.sendMessageInTransaction("tx_topic", message, null);
        log.info("已发送...");
    }
}
