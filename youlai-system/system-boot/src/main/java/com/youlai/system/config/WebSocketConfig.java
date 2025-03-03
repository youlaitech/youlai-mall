package com.youlai.system.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.youlai.system.event.UserConnectionEvent;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket 自动配置类
 *
 * @author haoxr
 * @since 2.4.0
 */
// 启用WebSocket消息代理功能和配置STOMP协议，实现实时双向通信和消息传递
@EnableWebSocketMessageBroker
@Configuration
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final ApplicationEventPublisher eventPublisher;

    public WebSocketConfig(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * 注册一个端点，客户端通过这个端点进行连接
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                // 注册 /ws 的端点
                .addEndpoint("/ws")
                // 允许跨域
                .setAllowedOriginPatterns("*");
    }


    /**
     * 配置消息代理
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 客户端发送消息的请求前缀
        registry.setApplicationDestinationPrefixes("/client");

        // 客户端订阅消息的请求前缀，topic一般用于广播推送，queue用于点对点推送
        registry.enableSimpleBroker("/topic", "/queue");

        // 服务端通知客户端的前缀，可以不设置，默认为user
        registry.setUserDestinationPrefix("/user");
    }


    /**
     * 配置客户端入站通道拦截器
     * <p>
     * 添加 ChannelInterceptor 拦截器，用于在消息发送前，从请求头中获取 token 并解析出用户信息(username)，用于点对点发送消息给指定用户
     *
     * @param registration 通道注册器
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(@NotNull Message<?> message, @NotNull MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (accessor != null) {
                    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                        String bearerToken = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
                        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
                            bearerToken = bearerToken.substring(BEARER_PREFIX.length());
                            String username = JWTUtil.parseToken(bearerToken).getPayloads().getStr(JWTPayload.SUBJECT);
                            if (StrUtil.isNotBlank(username)) {
                                accessor.setUser(() -> username);
                                eventPublisher.publishEvent(new UserConnectionEvent(this, username, true));
                            }
                        }
                    } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                        if (accessor.getUser() != null) {
                            String username = accessor.getUser().getName();
                            eventPublisher.publishEvent(new UserConnectionEvent(this, username, false));
                        }
                    }
                }
                return ChannelInterceptor.super.preSend(message, channel);
            }
        });
    }

}
