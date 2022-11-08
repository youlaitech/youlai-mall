package com.youlai.laboratory.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zc
 * @date 2022-10-27 00:56
 */
@Service
@Slf4j
public class BaseProducer {
    /**
     * 测试发送将参数topic定死，实际开发写入到配置文件
     */
    @Resource
    RocketMQTemplate rocketMQTemplate;

    /**
     * 这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知。
     */
    public void sync() {
        String text = "基本信息案例-同步发送," + System.currentTimeMillis();
        log.info(text);

        rocketMQTemplate.syncSend("base_topic", text);
        log.info("同步发送-已发送...");
    }

    /**
     * 异步消息通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间地等待Broker的响应。
     */
    public void async() {
        String text = "基本信息案例-异步发送," + System.currentTimeMillis();
        log.info(text);
        for (int a = 1; a <= 10; a++) {
            int finalA = a;
            rocketMQTemplate.asyncSend("base_topic", text + "，ID:" + a, new SendCallback() {

                // SendCallback接收异步返回结果的回调
                // 成功发送
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("异步发送 - 发送成功，ID:" + finalA);
                }

                // 发送失败
                @Override
                public void onException(Throwable throwable) {
                    log.info("异步发送 - 发送失败");
                    throwable.printStackTrace();
                }
            });
        }
        log.info("异步发送-已发送...");
    }

    /**
     * 这种方式主要用在不特别关心发送结果的场景，例如日志发送。
     */
    public void oneWay() {
        String text = "基本信息案例-单向发送" + System.currentTimeMillis();
        log.info(text);

        rocketMQTemplate.sendOneWay("base_topic", text);
        log.info("单向发送-已发送...");
    }

}
