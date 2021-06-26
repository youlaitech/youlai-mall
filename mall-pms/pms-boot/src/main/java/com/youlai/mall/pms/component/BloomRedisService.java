package com.youlai.mall.pms.component;

import com.google.common.base.Preconditions;
import com.youlai.mall.pms.utils.BloomFilterUtils;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author DaniR
 * @Description
 * @Date 2021/6/26 9:38
 **/
public class BloomRedisService {

    private RedisTemplate<String, Object> redisTemplate;

    private BloomFilterUtils bloomFilterUtils;

    public void setBloomFilterUtils(BloomFilterUtils bloomFilterUtils) {
        this.bloomFilterUtils = bloomFilterUtils;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 根据给定的布隆过滤器添加值
     */
    public <T> void addByBloomFilter(String key, T value) {
        Preconditions.checkArgument(bloomFilterUtils != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterUtils.murmurHash(value);
        for (int i : offset) {
            redisTemplate.opsForValue().setBit(key, i, true);
        }
    }

    /**
     * 根据给定的布隆过滤器判断值是否存在
     */
    public <T> boolean includeByBloomFilter(String key, T value) {
        Preconditions.checkArgument(bloomFilterUtils != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterUtils.murmurHash(value);
        for (int i : offset) {
            if (!redisTemplate.opsForValue().getBit(key, i)) {
                return false;
            }
        }
        return true;
    }
}
