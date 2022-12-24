package com.youlai.mall.pms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.common.constant.ProductConstants;
import com.youlai.mall.pms.mapper.PmsSkuMapper;
import com.youlai.mall.pms.pojo.dto.CheckPriceDTO;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.pojo.dto.LockStockDTO;
import com.youlai.mall.pms.pojo.entity.PmsSku;
import com.youlai.mall.pms.service.SkuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品SKU业务实现类
 *
 * @author haoxr
 * @date 2022/12/21
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SkuServiceImpl extends ServiceImpl<PmsSkuMapper, PmsSku> implements SkuService {

    private final RedisTemplate redisTemplate;
    private final RedissonClient redissonClient;

    /**
     * 获取商品库存数量
     *
     * @param skuId
     * @return
     */
    @Override
    @Cacheable(cacheNames = "pms", key = "'stock_num:'+#skuId")
    public Integer getStockNum(Long skuId) {

        PmsSku pmsSku = this.getOne(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getId, skuId)
                .select(PmsSku::getStockNum));
        Integer stockNum = pmsSku != null ? pmsSku.getStockNum() : 0;
        return stockNum;
    }

    /**
     * 锁定库存
     *
     * @param lockStock 订单编号 + 锁定商品集合
     * @return true/false
     */
    @Override
    @Transactional
    public boolean lockStock(LockStockDTO lockStock) {
        String orderSn = lockStock.getOrderSn();
        log.info("创建订单【{}】锁定商品库存:{}", orderSn, lockStock);

        List<LockStockDTO.LockedSku> lockedSkus = lockStock.getLockedSkus();

        Assert.isTrue(CollectionUtil.isNotEmpty(lockedSkus), "锁定的商品为空");

        // 循环遍历锁定商品
        for (LockStockDTO.LockedSku lockedSku : lockedSkus) {
            // 获取商品分布式锁
            RLock lock = redissonClient.getLock(ProductConstants.SKU_LOCK_PREFIX + lockedSku.getSkuId());
            // 加锁
            lock.lock();
            try {
                boolean lockResult = this.update(new LambdaUpdateWrapper<PmsSku>()
                        .setSql("locked_stock_num = locked_stock_num + " + lockedSku.getCount())
                        .eq(PmsSku::getId, lockedSku.getSkuId())
                        .apply("stock_num - locked_stock_num >= {0}", lockedSku.getCount())
                );
                Assert.isTrue(lockResult, "锁定商品 {} 失败", lockedSku.getSkuId());
            } finally {
                // 释放锁
                if (lock.isLocked()) {
                    lock.unlock();
                }
            }
        }
        /**
         * 将锁定的商品和数量缓存至Redis，用于后续的场景:
         *
         * 1.订单取消解锁库存；
         * 2.订单支付扣减库存。
         */

        redisTemplate.opsForValue().set(ProductConstants.ORDER_LOCKED_SKUS_PREFIX + orderSn, lockedSkus);

        return true;
    }

    /**
     * 解锁库存
     * <p>
     * 订单超时未支付，释放锁定的商品库存
     *
     * @return true/false
     */
    @Override
    public boolean unlockStock(String orderSn) {
        log.info("订单取消：释放订单【{}】锁定的商品库存", orderSn);
        List<LockStockDTO.LockedSku> lockedSkus = (List<LockStockDTO.LockedSku>) redisTemplate.opsForValue()
                .get(ProductConstants.ORDER_LOCKED_SKUS_PREFIX + orderSn);

        // 遍历解锁商品
        if (CollectionUtil.isNotEmpty(lockedSkus)) {
            for (LockStockDTO.LockedSku lockedSku : lockedSkus) {
                // 获取商品分布式锁
                RLock lock = redissonClient.getLock(ProductConstants.SKU_LOCK_PREFIX + lockedSku.getSkuId());
                // 加锁
                lock.lock();
                try {
                    this.update(new LambdaUpdateWrapper<PmsSku>()
                            .eq(PmsSku::getId, lockedSku.getSkuId())
                            .setSql("locked_stock_num = locked_stock_num - " + lockedSku.getCount()));
                } finally {
                    // 释放锁
                    if (lock.isLocked()) {
                        lock.unlock();
                    }
                }
            }
        }

        // 移除订单的商品信息缓存
        redisTemplate.delete(ProductConstants.ORDER_LOCKED_SKUS_PREFIX + orderSn);
        return true;
    }

    /**
     * 扣减库存
     * <p>
     * 订单支付完成扣减订单商品库存和释放锁定的库存
     *
     * @param orderSn 订单编号
     * @return ture/false
     */
    @Override
    public boolean deductStock(String orderSn) {
        log.info("订单【{}】支付成功：扣减订单商品库存", orderSn);

        // 获取订单提交时锁定的商品
        List<LockStockDTO.LockedSku> lockedSkus = (List<LockStockDTO.LockedSku>) redisTemplate.opsForValue()
                .get(ProductConstants.ORDER_LOCKED_SKUS_PREFIX + orderSn);

        if (CollectionUtil.isNotEmpty(lockedSkus)) {

            for (LockStockDTO.LockedSku lockedSku : lockedSkus) {
                // 获取商品分布式锁
                RLock lock = redissonClient.getLock(ProductConstants.SKU_LOCK_PREFIX + lockedSku.getSkuId());
                // 加锁
                lock.lock();
                try {
                    this.update(new LambdaUpdateWrapper<PmsSku>()
                            .eq(PmsSku::getId, lockedSku.getSkuId())
                            .setSql("stock_num = stock_num - " + lockedSku.getCount())
                            .setSql("locked_stock_num = locked_stock_num - " + lockedSku.getCount())
                    );
                } finally {
                    // 释放锁
                    if (lock.isLocked()) {
                        lock.unlock();
                    }
                }
            }

        }
        // 移除订单的商品缓存
        redisTemplate.delete(ProductConstants.ORDER_LOCKED_SKUS_PREFIX + orderSn);
        return true;
    }

    /**
     * 订单商品验价
     * <p>
     * 判断订单总金额和订单商品实时价格之和是否相等
     * 不相等：商品实时价格和页面价格不等，订单无效
     *
     * @param checkPrice 验价参数(订单总金额、订单商品明细)
     * @return true/false
     */
    @Override
    public boolean checkPrice(CheckPriceDTO checkPrice) {
        log.info("订单【{}】商品验价：{}", checkPrice);
        // 订单总金额
        Long orderTotalAmount = checkPrice.getOrderTotalAmount();
        // 计算商品总金额
        List<CheckPriceDTO.OrderSku> orderSkus = checkPrice.getOrderSkus();
        if (orderTotalAmount == null || CollectionUtil.isEmpty(orderSkus)) {
            log.warn("订单【{}】验价失败：订单总金额或订单商品为空，无法进行验价！");
            return false;
        }
        // 订单商品ID集合
        List<Long> orderSkuIds = orderSkus.stream().map(orderItem -> orderItem.getSkuId())
                .collect(Collectors.toList());
        // 获取商品的实时价格
        List<PmsSku> skuList = this.list(new LambdaQueryWrapper<PmsSku>().in(PmsSku::getId, orderSkuIds)
                .select(PmsSku::getId, PmsSku::getPrice)
        );
        if (CollectionUtil.isEmpty(skuList)) {
            log.warn("订单【{}】验价失败：订单商品库存不存在或已下架！");
            return false;
        }
        // 计算商品实时总价
        Long skuTotalAmount = skuList.stream().map(sku -> {
            // 获取订单中该商品数量
            CheckPriceDTO.OrderSku matchOrderSku = orderSkus.stream()
                    .filter(orderSku -> sku.getId().equals(orderSku.getSkuId()))
                    .findFirst().orElse(null);
            // 单个商品实时总价 = 实时单价 * 订单该商品数量
            return matchOrderSku != null ? sku.getPrice() * matchOrderSku.getCount() : 0L;
        }).reduce(0L, Long::sum);
        // 比较订单总价和商品实时总价
        boolean checkPriceResult = orderTotalAmount.compareTo(skuTotalAmount) == 0;
        return checkPriceResult;
    }

    /**
     * 获取商品库存信息
     *
     * @param skuId
     * @return
     */
    @Override
    public SkuDTO getSkuInfo(Long skuId) {
        SkuDTO skuInfo = this.baseMapper.getSkuInfo(skuId);
        return skuInfo;
    }

    /**
     * 「实验室」修改商品库存数量
     *
     * @param skuId
     * @param stockNum 商品库存数量
     * @return
     */
    @Override
    @Transactional
    public boolean updateStockNum(Long skuId, Integer stockNum) {
        boolean result = this.update(new LambdaUpdateWrapper<PmsSku>()
                .eq(PmsSku::getId, skuId)
                .set(PmsSku::getStockNum, stockNum)
        );
        return result;
    }

    /**
     * 「实验室」扣减商品库存
     *
     * @param skuId
     * @param num   商品库存数量
     * @return
     */
    @Override
    public boolean deductStock(Long skuId, Integer num) {
        boolean result = this.update(new LambdaUpdateWrapper<PmsSku>()
                .setSql("stock_num = stock_num - " + num)
                .eq(PmsSku::getId, skuId)
        );
        return result;
    }
}
