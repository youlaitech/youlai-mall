package com.youlai.gateway.config;

import com.youlai.gateway.component.RedisChannelListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisGatewyConfig {

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListenerAdapter(),channelTopic());
        return container;
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter(){
        return new MessageListenerAdapter(redisChannelListener());
    }

    @Bean
    RedisChannelListener redisChannelListener(){
        return new RedisChannelListener();
    }
    @Bean
    ChannelTopic channelTopic(){
        return new ChannelTopic("cleanRoleLocalCache");
    }

}
