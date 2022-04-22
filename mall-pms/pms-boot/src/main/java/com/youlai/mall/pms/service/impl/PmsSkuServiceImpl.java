package com.youlai.mall.pms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.pms.common.constant.PmsConstants;
import com.youlai.mall.pms.mapper.PmsSkuMapper;
import com.youlai.mall.pms.pojo.dto.CheckPriceDTO;
import com.youlai.mall.pms.pojo.dto.SkuInfoDTO;
import com.youlai.mall.pms.pojo.dto.app.LockStockDTO;
import com.youlai.mall.pms.pojo.entity.PmsSku;
import com.youlai.mall.pms.service.IPmsSkuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PmsSkuServiceImpl extends ServiceImpl<PmsSkuMapper, PmsSku> implements IPmsSkuService {

    private final StringRedisTemplate redisTemplate;
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
        Integer stockNum = 0;
        PmsSku pmsSku = this.getOne(new LambdaQueryWrapper<PmsSku>()
                .eq(PmsSku::getId, skuId)
                .select(PmsSku::getStockNum));

        if (pmsSku != null) {
            stockNum = pmsSku.getStockNum();
        }
        return stockNum;
    }

    /**
     * 锁定库存 - 订单提交
     */
    @Override
    @Transactional
    public boolean lockStock(LockStockDTO lockStockDTO) {
        log.info("锁定商品库存:{}", JSONUtil.toJsonStr(lockStockDTO));

        List<LockStockDTO.LockedSku> lockedSkuList = lockStockDTO.getLockedSkuList();
        Assert.isTrue(CollectionUtil.isNotEmpty(lockedSkuList), "锁定的商品为空");

        // 循环遍历锁定商品
        lockedSkuList.forEach(lockedSku -> {
            RLock lock = redissonClient.getLock(PmsConstants.LOCK_SKU_PREFIX + lockedSku.getSkuId()); // 获取分布式锁
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
                lock.unlock();
            }
        });

        // 将锁定的商品ID和对应购买数量持久化至Redis，后续使用场景: 1.订单取消归还库存;2.订单支付成功扣减库存。
        String orderToken = lockStockDTO.getOrderToken();
        redisTemplate.opsForValue().set(PmsConstants.LOCKED_STOCK_PREFIX + orderToken, JSONUtil.toJsonStr(lockedSkuList));

        // 无异常直接返回true
        return true;
    }

    /**
     * 释放库存 - 订单超时未支付
     */
    @Override
    public boolean unlockStock(String orderToken) {
        log.info("释放库存,orderToken:{}", orderToken);
        String lockedSkuJsonStr = redisTemplate.opsForValue().get(PmsConstants.LOCKED_STOCK_PREFIX + orderToken);
        List<LockStockDTO.LockedSku> lockedSkuList = JSONUtil.toList(lockedSkuJsonStr, LockStockDTO.LockedSku.class);
        lockedSkuList.forEach(item ->
                this.update(new LambdaUpdateWrapper<PmsSku>()
                        .eq(PmsSku::getId, item.getSkuId())
                        .setSql("locked_stock_num = locked_stock_num - " + item.getCount()))
        );

        // 删除redis中锁定的库存
        redisTemplate.delete(PmsConstants.LOCKED_STOCK_PREFIX + orderToken);
        return true;
    }

    /**
     * 扣减库存 - 支付成功
     */
    @Override
    public boolean deductStock(String orderToken) {
        log.info("扣减库存，orderToken:{}", orderToken);
        String lockedSkuJsonStr = redisTemplate.opsForValue().get(PmsConstants.LOCKED_STOCK_PREFIX + orderToken);
        List<LockStockDTO.LockedSku> lockedSkuList = JSONUtil.toList(lockedSkuJsonStr, LockStockDTO.LockedSku.class);

        lockedSkuList.forEach(item -> {
            boolean result = this.update(new LambdaUpdateWrapper<PmsSku>()
                    .eq(PmsSku::getId, item.getSkuId())
                    .setSql("stock_num = stock_num - " + item.getCount())
                    .setSql("locked_stock_num = locked_stock_num - " + item.getCount())
            );
            if (!result) {
                throw new BizException("扣减库存失败,商品" + item.getSkuId() + "库存不足");
            }
        });

        // 删除redis中锁定的库存
        redisTemplate.delete(PmsConstants.LOCKED_STOCK_PREFIX + orderToken);
        return true;
    }

    /**
     * 商品验价
     *
     * @param checkPriceDTO
     * @return
     */
    @Override
    public boolean checkPrice(CheckPriceDTO checkPriceDTO) {
        Long orderTotalAmount = checkPriceDTO.getOrderTotalAmount(); // 订单总金额

        // 计算商品总金额
        List<CheckPriceDTO.CheckSku> checkOrderItems = checkPriceDTO.getCheckSkus();
        if (CollectionUtil.isNotEmpty(checkOrderItems)) {
            List<Long> skuIds = checkOrderItems.stream()
                    .map(orderItem -> orderItem.getSkuId()).collect(Collectors.toList());
            List<PmsSku> skuList = this.list(new LambdaQueryWrapper<PmsSku>().in(PmsSku::getId, skuIds)
                    .select(PmsSku::getId, PmsSku::getPrice));
            // 商品总金额
            Long skuTotalAmount = checkOrderItems.stream().map(checkOrderItem -> {
                Long skuId = checkOrderItem.getSkuId();
                PmsSku pmsSku = skuList.stream().filter(sku -> sku.getId().equals(skuId)).findFirst().orElse(null);
                if (pmsSku != null) {
                    return pmsSku.getPrice() * checkOrderItem.getCount();
                }
                return 0L;
            }).reduce(0L, Long::sum);
            return orderTotalAmount.compareTo(skuTotalAmount) == 0;
        }
        return false;
    }

    /**
     * 获取商品库存信息
     *
     * @param skuId
     * @return
     */
    @Override
    public SkuInfoDTO getSkuInfo(Long skuId) {
        SkuInfoDTO skuInfo = this.baseMapper.getSkuInfo(skuId);
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
