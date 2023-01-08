package com.youlai.system.listener.rabbitmq;

import com.youlai.common.web.model.CanalMessage;
import com.youlai.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Canal + RabbitMQ 监听数据库数据变化
 *
 * @author haoxr
 * @date 2021/11/4 23:14
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CanalListener {

    private final SysMenuService menuService;

    //@RabbitListener(queues = "canal.queue")
    public void handleDataChange(@Payload CanalMessage message) {
        String tableName = message.getTable();

        log.info("Canal 监听 {} 发生变化；明细：{}", tableName, message);
        if (Arrays.asList("sys_menu", "sys_role", "sys_role_menu").contains(tableName)) {
            log.info("======== 清理菜单路由缓存 ========");
            menuService.cleanCache();
        }
    }
}
