package com.youlai.gateway.component;

import cn.hutool.core.util.StrUtil;
import com.youlai.common.constant.GlobalConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.nio.charset.StandardCharsets;

public class RedisChannelListener implements MessageListener {

    @Autowired
    private AdminRoleLocalCache adminRoleLocalCache;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        adminRoleLocalCache.remove(GlobalConstants.URL_PERM_ROLES_KEY);

    }
}
