package com.youlai.laboratory.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author zc
 * @date 2022-10-27 00:59
 */
@Service
@Slf4j
public class OrderProducer {
    /**
     * 测试发送将参数topic定死，实际开发写入到配置文件
     */
    @Resource
    RocketMQTemplate rocketMQTemplate;

    public void order() {
        log.info("顺序消息");
        try {
            for (int i = 1; i <= 10; i++) {
                int num = (int) (Math.random() * 10000);
                // 设置一个延时，表示同一个消息先后进入到队形中
                TimeUnit.MILLISECONDS.sleep(50);
                log.info("顺序消息,ID:" + num);
                // 第一个参数为topic，第二个参数为内容，第三个参数为Hash值，不同hash值在不同的队列中
                rocketMQTemplate.syncSendOrderly("order_topic", "顺序消息,ID:" + num, "order");
            }
            log.info("已发送...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

