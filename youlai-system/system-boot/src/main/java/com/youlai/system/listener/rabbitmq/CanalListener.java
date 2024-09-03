package com.youlai.system.listener.rabbitmq;

import com.youlai.system.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Canal + RabbitMQ 监听数据库数据变化
 *
 * @author Ray
 * @since 2021/11/4 23:14
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CanalListener {

    private final MenuService menuService;

    //@RabbitListener(queues = "canal.queue")
    public void handleDataChange() {

    }
}
