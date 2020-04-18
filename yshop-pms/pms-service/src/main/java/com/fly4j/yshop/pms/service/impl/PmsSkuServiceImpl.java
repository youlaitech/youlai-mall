package com.fly4j.yshop.pms.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.yshop.pms.mapper.PmsSkuMapper;
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import com.fly4j.yshop.pms.pojo.entity.PmsSkuLock;
import com.fly4j.yshop.pms.pojo.vo.SkuLockVO;
import com.fly4j.yshop.pms.service.IPmsSkuLockService;
import com.fly4j.yshop.pms.service.IPmsSkuService;
import org.apache.commons.collections.CollectionUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PmsSkuServiceImpl extends ServiceImpl<PmsSkuMapper, PmsSku> implements IPmsSkuService {

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IPmsSkuLockService iPmsSkuLockService;

    private static final String LOCK_PREFIX = "sku:lock:";

    @Override
    public String checkAndLockStock(List<SkuLockVO> skuLockVOS) {
        // 未锁住的库存
        List<SkuLockVO> notLockSkus = new ArrayList<>();
        // 记录已锁住的库存
        List<SkuLockVO> lockedSkus = new ArrayList<>();

        // 遍历所有skuLockVO，锁定库存
        skuLockVOS.forEach(skuLockVO -> {
            System.out.println("锁库存开始。。。。");
            lockSku(notLockSkus, lockedSkus, skuLockVO);
            System.out.println("锁库存结束。。。。");
        });

        // 如果没有锁住的集合为空
        if (CollectionUtils.isEmpty(notLockSkus)) {
            // 代表都锁住了, 把锁定的库存信息放入redis，方便后续释放库存
            String orderToken = skuLockVOS.get(0).getOrder_token();
            this.stringRedisTemplate.opsForValue().set(LOCK_PREFIX + orderToken, JSON.toJSONString(lockedSkus));
            return null;
        }
        // 一旦库存锁不住，回滚已锁的库存，并提示页面那些商品的库存没锁住
        for (SkuLockVO skuLock : lockedSkus) {
            iPmsSkuLockService.unLockSku(skuLock);
        }

        List<Long> skuIds = notLockSkus.stream().map(skuLockVO -> skuLockVO.getSku_id()).collect(Collectors.toList());

        return "锁定失败：商品" + skuIds.toString() + "库存不足";
    }

    private void lockSku(List<SkuLockVO> notLockSkus, List<SkuLockVO> lockedSkus, SkuLockVO skuLockVO) {
        // 只锁当前的sku
        RLock lock = this.redissonClient.getFairLock(LOCK_PREFIX + skuLockVO.getSku_id());
        lock.lock();

        List<PmsSkuLock> skuEntities = iPmsSkuLockService.getAllCanLocked(skuLockVO);
        if (skuEntities != null && skuEntities.size() > 0) {
            // 拿到第一个仓库锁库存
            PmsSkuLock skuEntity = skuEntities.get(0);
            long i = iPmsSkuLockService.lockSku(skuEntity.getId(), skuLockVO.getQuantity());
            if (i > 0) {
                skuLockVO.setSku_id(skuEntity.getId());
                lockedSkus.add(skuLockVO);
            } else {
                notLockSkus.add(skuLockVO);
            }
        } else {
            notLockSkus.add(skuLockVO);
        }
        lock.unlock();
    }
}
