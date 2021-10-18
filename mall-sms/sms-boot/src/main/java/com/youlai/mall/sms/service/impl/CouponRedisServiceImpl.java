package com.youlai.mall.sms.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.sms.pojo.domain.SmsCoupon;
import com.youlai.mall.sms.pojo.enums.CouponStateEnum;
import com.youlai.mall.sms.service.ICouponRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.youlai.common.constant.RedisConstants.*;

/**
 * @author xinyi
 * @desc：优惠券 Redis 相关操作服务接口实现
 * @date 2021/7/4
 */
@Slf4j
@Service
public class CouponRedisServiceImpl implements ICouponRedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<SmsCoupon> getCachedCoupons(Long userId, Integer state) {
        log.info("Get Coupons From Cache, User:{}, State:{}", userId, state);
        String redisKey = state2RedisKey(userId, state);
        List<String> couponCaches = (List<String>) redisTemplate.opsForHash()
                .values(redisKey)
                .stream().map(obj -> Objects.toString(obj, null))
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(couponCaches)) {
            // 在缓存中放一个无效的优惠券，防止缓存穿透
            saveEmptyCouponListToCache(userId, Collections.singletonList(state));
            return Collections.emptyList();
        }
        return couponCaches.stream()
                .map(obj -> JSONUtil.toBean(obj, SmsCoupon.class))
                .collect(Collectors.toList());
    }

    @Override
    public void saveEmptyCouponListToCache(Long userId, List<Integer> states) {
        log.info("Save Empty Coupon List To Cache For User:{}, State:{}", userId, JSONUtil.toJsonStr(states));
        Map<String, String> invalidCouponMap = new HashMap<>();
        // 构造无效的coupon
        invalidCouponMap.put("-1", JSONUtil.toJsonStr(new SmsCoupon()));

        SessionCallback<Object> sessionCallback = new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for (Integer state : states) {
                    String redisKey = state2RedisKey(userId, state);
                    operations.opsForHash().putAll(redisKey, invalidCouponMap);
                }
                return null;
            }
        };

        log.info("Pipeline Exe Result: {}", redisTemplate.executePipelined(sessionCallback));
    }

    @Override
    public String tryToAcquireCouponCodeFromCache(String templateId) {
        String redisKey = String.format("%s%s", SMS_COUPON_TEMPLATE_CODE_KEY, templateId);
        String couponCode = (String) redisTemplate.opsForList().leftPop(redisKey);
        log.info("Acquire Coupon Code, {} {} {}", templateId, redisKey, couponCode);
        return couponCode;
    }

    @Override
    public Integer addCouponToCache(Long userId, List<SmsCoupon> coupons, Integer state) {
        log.info("Add Coupon To Cache: {}, {}, {}", userId, coupons, state);
        int result = -1;

        CouponStateEnum couponStateEnum = CouponStateEnum.of(state);
        switch (couponStateEnum) {
            case USABLE:
                result = addCouponToCacheForUsable(userId, coupons);
                break;
            case USED:
                result = addCouponToCacheForUsed(userId, coupons);
                break;
            case EXPIRED:
                result = addCouponToCacheForExpired(userId, coupons);
                break;
        }
        return result;
    }


    private Integer addCouponToCacheForUsable(Long userId, List<SmsCoupon> coupons) {

        log.debug("Add Coupon To Cache For Usable.");
        Map<String, String> needCacheForUsable = new HashMap<>(coupons.size());
        coupons.forEach(coupon -> {
            needCacheForUsable.put(coupon.getId().toString(), JSONUtil.toJsonStr(coupon));
        });
        String redisKey = state2RedisKey(userId, CouponStateEnum.USABLE.getCode());
        redisTemplate.opsForHash().putAll(redisKey, needCacheForUsable);
        log.info("Add Coupons To Cache For Usable: {}, {}, {}", userId, redisKey, needCacheForUsable.size());

        redisTemplate.expire(redisKey, getRandomExpirationTime(1, 2), TimeUnit.SECONDS);
        return needCacheForUsable.size();
    }

    private Integer addCouponToCacheForUsed(Long userId, List<SmsCoupon> coupons) {

        log.debug("Add Coupon To Cache For Used.");
        Map<String, String> needCacheForUsed = new HashMap<>();

        String redisKeyForUsable = state2RedisKey(userId, CouponStateEnum.USABLE.getCode());
        String redisKeyForUsed = state2RedisKey(userId, CouponStateEnum.USED.getCode());
        // TODO 待开发
        return coupons.size();
    }

    private int addCouponToCacheForExpired(Long userId, List<SmsCoupon> coupons) {
        log.debug("Add Coupon To Cache For Expired.");
        Map<String, String> needCacheForExpired = new HashMap<>(coupons.size());
        String redisKeyForUsable = state2RedisKey(userId, CouponStateEnum.USABLE.getCode());
        String redisKeyForExpired = state2RedisKey(userId, CouponStateEnum.EXPIRED.getCode());
        // 从缓存中获取当前可用优惠券列表和无效优惠券列表
        List<SmsCoupon> curUsableCoupons = getCachedCoupons(userId, CouponStateEnum.USABLE.getCode());
        List<SmsCoupon> curExpiredCoupons = getCachedCoupons(userId, CouponStateEnum.EXPIRED.getCode());

        assert curUsableCoupons.size() > coupons.size();
        coupons.forEach(coupon -> {
            needCacheForExpired.put(coupon.getId().toString(), JSONUtil.toJsonStr(coupon));
        });

        List<Long> curUsableIds = curUsableCoupons.stream().map(SmsCoupon::getId).collect(Collectors.toList());
        List<Long> paramIds = coupons.stream().map(SmsCoupon::getId).collect(Collectors.toList());
        if (!CollectionUtils.isSubCollection(curUsableIds, paramIds)) {
            log.error("CurCoupons Is Not Equal To Cache: {}, {}, {}", userId, JSONUtil.toJsonStr(curUsableIds), JSONUtil.toJsonStr(paramIds));
            throw new BizException("CurCoupon Is Not Equal To Cache.");
        }
        List<String> needCleanKey = paramIds.stream().map(String::valueOf).collect(Collectors.toList());

        SessionCallback<Objects> sessionCallback = new SessionCallback<Objects>() {
            @Override
            public Objects execute(RedisOperations operations) throws DataAccessException {

                // 1、已过期优惠券缓存
                operations.opsForHash().putAll(redisKeyForExpired, needCacheForExpired);
                // 2、可用的优惠券需要清理
                operations.opsForHash().delete(redisKeyForUsable, needCleanKey);
                //3、重置过期时间
                operations.expire(redisKeyForUsable, getRandomExpirationTime(1, 2), TimeUnit.SECONDS);
                operations.expire(redisKeyForExpired, getRandomExpirationTime(1, 2), TimeUnit.SECONDS);
                return null;
            }
        };

        log.info("Pipeline Exe Result: {}", redisTemplate.executePipelined(sessionCallback));

        return coupons.size();
    }

    private String state2RedisKey(Long userId, Integer state) {
        String redisKey = null;
        CouponStateEnum couponStateEnum = CouponStateEnum.of(state);
        switch (couponStateEnum) {
            case USABLE:
                redisKey = String.format("%s%s", SMS_USER_COUPON_USABLE_KEY, userId);
                break;
            case USED:
                redisKey = String.format("%s%s", SMS_USER_COUPON_USED_KEY, userId);
                break;
            case EXPIRED:
                redisKey = String.format("%s%s", SMS_USER_COUPON_EXPIRED_KEY, userId);
                break;
            default:
                break;
        }
        return redisKey;
    }

    /**
     * 获取一个随机的过期时间
     * 目的：解决Key在同一时间失效
     *
     * @param min 最小的小时数
     * @param max 最大的小时数
     * @return 返回 [min,max] 之间的随机秒数
     */
    private Long getRandomExpirationTime(Integer min, Integer max) {
        return RandomUtils
                .nextLong(min * 60 * 60, max * 60 * 60);
    }
}
