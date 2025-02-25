package com.youlai.system.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户连接事件
 *
 * @author Ray.Hao
 * @since 4.0.0
 */
@Getter
public class UserConnectionEvent extends ApplicationEvent {

    /**
     * 用户名
     */
    private final String username;

    /**
     * 是否连接
     */
    private final boolean connected;

    /**
     * 用户连接事件
     *
     * @param source    事件源
     * @param username  用户名
     * @param connected 是否连接
     */
    public UserConnectionEvent(Object source, String username, boolean connected) {
        super(source);
        this.username = username;
        this.connected = connected;
    }
}