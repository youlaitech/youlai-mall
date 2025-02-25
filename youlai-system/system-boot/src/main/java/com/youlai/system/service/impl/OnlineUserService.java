package com.youlai.system.service.impl;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 在线用户服务
 *
 * @author haoxr
 * @since 2024/9/26
 */

@Service
public class OnlineUserService {

    private final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();

    /**
     * 添加用户到在线用户集合
     *
     * @param username 用户名
     */
    public void addOnlineUser(String username) {
        onlineUsers.add(username);
    }

    /**
     * 从在线用户集合移除用户
     *
     * @param username 用户名
     */
    public void removeOnlineUser(String username) {
        onlineUsers.remove(username);
    }

    /**
     * 获取所有在线用户
     *
     * @return 在线用户集合
     */
    public Set<String> getAllOnlineUsers() {
        return Collections.unmodifiableSet(onlineUsers);
    }

    /**
     * 获取在线的接收者
     * 从所有接收者中过滤出在线的接收者
     *
     * @param receivers 接收者
     * @return 在线的接收者集合
     */
    public Set<String> getOnlineReceivers(Set<String> receivers) {
        return receivers.stream().filter(onlineUsers::contains).collect(Collectors.toSet());
    }

    /**
     * 获取在线用户数量
     *
     * @return 在线用户数量
     */
    public int getOnlineUserCount() {
        return onlineUsers.size();
    }


}
