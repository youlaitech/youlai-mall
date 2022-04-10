package com.youlai.admin.component.listener;

import cn.hutool.json.JSONUtil;
import com.youlai.admin.service.ISysMenuService;
import com.youlai.admin.service.ISysOauthClientService;
import com.youlai.admin.service.ISysPermissionService;
import com.youlai.common.dto.CanalMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Canal + RabbitMQ 监听数据库数据变化
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021/11/4 23:14
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CanalListener {

    private final ISysPermissionService permissionService;
    private final ISysOauthClientService oauthClientService;
    private final ISysMenuService menuService;

    /*@RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "canal.queue", durable = "true"),
                    exchange = @Exchange(value = "canal.exchange"),
                    key = "canal.routing.key"
            )
    })*/
    @RabbitListener(queues = "canal.queue")
    public void handleDataChange(String message) {
        CanalMessage canalMessage = JSONUtil.toBean(message, CanalMessage.class);
        String tableName = canalMessage.getTable();

        log.info("Canal 监听 {} 发生变化；明细：{}", tableName, message);

        if ("sys_oauth_client".equals(tableName)) {
            log.info("======== 清除客户端信息缓存 ========");
            oauthClientService.cleanCache();
        } else if (Arrays.asList("sys_permission", "sys_role", "sys_role_permission").contains(tableName)) {
            log.info("======== 刷新角色权限缓存 ========");
            permissionService.refreshPermRolesRules();
        } else if (Arrays.asList("sys_menu", "sys_role", "sys_role_menu").contains(tableName)) {
            log.info("======== 清理菜单路由缓存 ========");
            menuService.cleanCache();
        }
    }
}
