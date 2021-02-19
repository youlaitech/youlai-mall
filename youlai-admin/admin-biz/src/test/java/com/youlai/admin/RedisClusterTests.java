package com.youlai.admin;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
public class RedisClusterTests {


    private final Integer MAX_THREADS = 10;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private final Integer REDIS_MASTER_NUM = 3;

    @Autowired
    private RedisTemplate redisTemplate;


    private HashMap map;

    RedisClusterTests() {
        map = new HashMap();
        map.put("name", "Tom");
        map.put("age", 27);
    }


    @Test
    public void testRedis() throws InterruptedException {
        for (int i = 0; i < MAX_THREADS; i++) {
            Thread t = new Thread(() -> {
                try {
                    countDownLatch.await();
                    while (true) {
                        redisTemplate.opsForValue().set("map", map);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
        countDownLatch.countDown();
        Thread.sleep(10000);

        Object map = redisTemplate.opsForValue().get("map");
        log.info(map.toString());
    }


    @Test
    public void testGenerateOrderSn() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String dateTime = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);

        String key = "order_sn:" + dateTime;
        Long increment = redisTemplate.opsForValue().increment(key, REDIS_MASTER_NUM);
        redisTemplate.expire(key, 2, TimeUnit.SECONDS);
        log.info(dateTime + String.format("%06d", increment));
    }

}
