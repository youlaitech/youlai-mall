package com.youlai.mall.pms.config;

import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import com.youlai.mall.pms.common.constant.PmsConstants;
import com.youlai.mall.pms.component.BloomRedisService;
import com.youlai.mall.pms.pojo.entity.PmsSpu;
import com.youlai.mall.pms.service.IPmsSpuService;
import com.youlai.mall.pms.utils.BloomFilterUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import java.util.List;

/**
 * @Author DaniR
 * @Description
 * @Date 2021/6/26 9:39
 **/
@Slf4j
@Configuration
@AllArgsConstructor
public class BloomFilterConfig implements InitializingBean {

    private final IPmsSpuService spuService;
    private final RedisTemplate redisTemplate;

    @Bean
    public BloomFilterUtils<String> initBloomFilterHelper() {
        return new BloomFilterUtils<>((Funnel<String>) (from, into) -> into.putString(from, Charsets.UTF_8)
                .putString(from, Charsets.UTF_8), 1000000, 0.01);
    }


    @Bean
    public BloomRedisService bloomRedisService() {
        BloomRedisService bloomRedisService = new BloomRedisService();
        bloomRedisService.setBloomFilterUtils(initBloomFilterHelper());
        bloomRedisService.setRedisTemplate(redisTemplate);
        return bloomRedisService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<PmsSpu> list = spuService.list();
        log.info("加载产品到布隆过滤器当中,size:{}", list.size());
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().filter(item -> item.getId() > 0).forEach(item -> {
                bloomRedisService().addByBloomFilter(PmsConstants.GOODS_BLOOM_FILTER, item.getId() + "");
            });
        }
    }
}
