package com.youlai.mall.oms.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class OrderSnGenerator {

    private final Integer REDIS_MASTER_NUM = 3;

    @Autowired
    private RedisTemplate redisTemplate;

    public String generate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String dateTime = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
        String key = "order_sn:" + dateTime;
        Long increment = redisTemplate.opsForValue().increment(key, REDIS_MASTER_NUM);
        redisTemplate.expire(key, 120, TimeUnit.SECONDS);
        return dateTime + String.format("%06d", increment);
    }
}
