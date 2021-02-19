package com.youlai.common.redis.component;

import com.youlai.common.enums.BusinessTypeEnum;
import com.youlai.common.redis.constant.RedisKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


@Component
@Slf4j
public class BusinessNoGenerator {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @param businessCode 业务类型编号
     * @param digit        业务序号位数
     * @return
     */
    public String generate(String businessCode, Integer digit) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
        String key = RedisKeyConstants.BUSINESS_NO_PREFIX + BusinessTypeEnum.getValue(businessCode).toString().toLowerCase() + ":" + date;
        Long increment = redisTemplate.opsForValue().increment(key);
        return date + businessCode + String.format("%0" + digit + "d", increment);
    }


    public String generate(String businessCode) {
        Integer defaultDigit = 6;
        return generate(businessCode, defaultDigit);
    }

}
