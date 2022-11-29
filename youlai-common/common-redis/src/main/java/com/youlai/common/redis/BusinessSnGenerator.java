package com.youlai.common.redis;

import com.youlai.common.constant.RedisConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@RequiredArgsConstructor
public class BusinessSnGenerator {

    private final RedisTemplate redisTemplate;

    /**
     * @param digit        业务序号位数
     * @return
     */
    public String generateSerialNo(Integer digit) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
        String key = RedisConstants.BUSINESS_NO_PREFIX +":" + date;
        Long increment = redisTemplate.opsForValue().increment(key);
        return date + String.format("%0" + digit + "d", increment);
    }

    public String generateSerialNo(){
       return this.generateSerialNo(6);
    }

}
