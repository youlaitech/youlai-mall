package com.youlai.mall.sms.service.impl;

import cn.hutool.core.date.format.FastDateFormat;
import com.google.common.base.Stopwatch;
import com.youlai.common.constant.RedisConstants;
import com.youlai.common.redis.utils.RedisUtils;
import com.youlai.mall.sms.mapper.SmsCouponTemplateMapper;
import com.youlai.mall.sms.pojo.domain.SmsCouponTemplate;
import com.youlai.mall.sms.service.IAsyncService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author xinyi
 * @desc：异步服务接口实现
 * @date 2021/6/27
 */
@Slf4j
@Service
@AllArgsConstructor
public class AsyncServiceImpl implements IAsyncService {

    private final RedisUtils redisUtils;

    private final SmsCouponTemplateMapper couponTemplateMapper;

    @Async("getAsyncExecutor")
    @Override
    public void asyncConstructCouponByTemplate(SmsCouponTemplate template) {
        Stopwatch watch = Stopwatch.createStarted();
        Set<String> couponCodes = buildCouponCode(template);
        String couponCodeKey = String.format("%s%s", RedisConstants.SMS_COUPON_TEMPLATE_CODE_KEY, template.getId());
        redisUtils.lSet(couponCodeKey, couponCodes);
        log.info("Push CouponCode To Redis, Coupon Template " +
                "ID:{}, Name:{}, TOTAL:{}", template.getId(), template.getName(), template.getTotal());
        couponTemplateMapper.updateById(template);

        watch.stop();
        log.info("Construct CouponCode By Coupon Template Use:{}ms", watch.elapsed(TimeUnit.MILLISECONDS));
    }

    /**
     * 构造优惠券码
     * 优惠券码对应于每一张优惠券，一共18位
     * 前四位：产品线+类型
     * 中间六位：日期随机
     * 后八位：0 ~ 9 随机数构成
     *
     * @param template {@link SmsCouponTemplate} 优惠券模板实体类
     * @return Set<String> 与 template.total 总优惠券数量
     */
    private Set<String> buildCouponCode(SmsCouponTemplate template) {

        Stopwatch watch = Stopwatch.createStarted();
        Set<String> result = new HashSet<>(template.getTotal());
        String prefix4 = template.getCategory().getCode();

        String date = FastDateFormat.getInstance("yyMMdd").format(new Date());
        for (Integer i = 0; i < template.getTotal(); i++) {
            result.add(buildCouponCodeSuffix14(date));
        }
        while (result.size() < template.getTotal()) {
            result.add(prefix4 + buildCouponCodeSuffix14(date));
        }

        // 断言
        assert result.size() == template.getTotal();
        watch.stop();
        log.info("Build Coupon Code use:{}ms", watch.elapsed(TimeUnit.MILLISECONDS));
        return result;
    }

    /**
     * 构造优惠券码后十四位
     *
     * @param date
     * @return
     */
    private String buildCouponCodeSuffix14(String date) {
        char[] bases = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};

        // 中间六位
        List<Character> chars = date.chars().mapToObj(obj -> (char) obj).collect(Collectors.toList());
        Collections.shuffle(chars);
        String mid6 = chars.stream().map(Objects::toString).collect(Collectors.joining());

        // 后八位
        String suffix8 = RandomStringUtils.random(1, bases) + RandomStringUtils.randomNumeric(7);
        return mid6 + suffix8;
    }
}
