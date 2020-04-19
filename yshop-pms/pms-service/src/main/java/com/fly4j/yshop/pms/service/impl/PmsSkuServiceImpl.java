package com.fly4j.yshop.pms.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.yshop.pms.mapper.PmsSkuMapper;
import com.fly4j.yshop.pms.pojo.dto.PmsSkuDTO;
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import com.fly4j.yshop.pms.pojo.vo.SkuLockVO;
import com.fly4j.yshop.pms.service.IPmsSkuService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class PmsSkuServiceImpl extends ServiceImpl<PmsSkuMapper, PmsSku> implements IPmsSkuService {

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private AmqpTemplate amqpTemplate;

    private static final String LOCK_PREFIX = "sku:lock:";

    @Override
    public String checkAndLockStock(List<SkuLockVO> skuLockVOS) {
        if (org.springframework.util.CollectionUtils.isEmpty(skuLockVOS)) {
            return "没有选中商品";
        }

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
            // 将锁定的商品信息保存在redis
            String orderToken = skuLockVOS.get(0).getOrder_token();
            this.stringRedisTemplate.opsForValue().set(LOCK_PREFIX + orderToken, JSON.toJSONString(lockedSkus));

            // 锁定库存后,发送延时消息,定时释放库存
            amqpTemplate.convertAndSend("STOCK-EXCHANGE", "stock.create", orderToken);

            return null;
        }
        // 一旦库存锁不住，回滚已锁的库存，并提示页面那些商品的库存没锁住
        for (SkuLockVO skuLock : lockedSkus) {
            this.baseMapper.unLockSku(skuLock);
        }
        List<Long> skuIds = notLockSkus.stream().map(skuLockVO -> skuLockVO.getSku_id()).collect(Collectors.toList());

        return "商品" + skuIds.toString() + "库存不足,请重新购买";

    }

    @Override
    public void unlockSku(String orderToken) {
        // 查询redis中保存的锁库存信息
        System.out.println("解库存开始。。。。。。");
        String stockLockJson = this.stringRedisTemplate.opsForValue().get(LOCK_PREFIX + orderToken);
        if (StringUtils.isNotBlank(stockLockJson)){
            List<SkuLockVO> skuLockVOS = JSON.parseArray(stockLockJson, SkuLockVO.class);
            skuLockVOS.forEach(skuLockVO -> {
                this.baseMapper.unLockSku(skuLockVO);
            });
        }
        System.out.println("解库存结束。。。。。。");
    }

    @Override
    public Integer minusStock(Long sku_id, Integer sku_quantity) {
        return this.baseMapper.minusStock(sku_id,sku_quantity);
    }

    private void lockSku(List<SkuLockVO> notLockSkus, List<SkuLockVO> lockedSkus, SkuLockVO skuLockVO) {
        // 只锁当前的sku
        RLock lock = this.redissonClient.getFairLock(LOCK_PREFIX + skuLockVO.getSku_id());
        lock.lock();

        SkuLockVO skuEntity = this.baseMapper.getCanLocked(skuLockVO);
        if (skuEntity != null) {
            // 锁库存
            long i = this.baseMapper.lockSku(skuLockVO.getSku_id(), skuLockVO.getQuantity());
            if (i > 0) {
                lockedSkus.add(skuLockVO);
            } else {
                notLockSkus.add(skuLockVO);
            }
        } else {
            notLockSkus.add(skuLockVO);
        }
        lock.unlock();
    }


    @Override
    public Page<PmsSkuDTO> selectPage(Map<String, Object> params, Page<PmsSku> page) {
        return this.baseMapper.page(params,page);
    }
}
