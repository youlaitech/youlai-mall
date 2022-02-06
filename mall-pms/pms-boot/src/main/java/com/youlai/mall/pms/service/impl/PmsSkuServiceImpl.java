package com.youlai.mall.pms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.result.Result;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.pms.common.constant.PmsConstants;
import com.youlai.mall.pms.mapper.PmsSkuMapper;
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
     * 创建订单时锁定库存
     */
    @Override
    public Result lockStock(List<LockStockDTO> skuLockList) {
        log.info("=======================创建订单，开始锁定商品库存=======================");
        log.info("锁定商品信息：{}", skuLockList.toString());
        if (CollectionUtil.isEmpty(skuLockList)) {
            return Result.failed("锁定的商品列表为空");
        }
        //prepareSkuLockList(null,  skuLockList);
        // 锁定商品
        skuLockList.forEach(item -> {
            RLock lock = redissonClient.getLock(PmsConstants.LOCK_SKU_PREFIX + item.getSkuId()); // 获取商品的分布式锁
            lock.lock();
            boolean result = this.update(new LambdaUpdateWrapper<PmsSku>()
                    .setSql("locked_stock = locked_stock + " + item.getCount())
                    .eq(PmsSku::getId, item.getSkuId())
                    .apply("stock - locked_stock >= {0}", item.getCount())
            );
            if (result) {
                item.setLocked(true);
            } else {
                item.setLocked(false);
            }
            lock.unlock();
        });

        // 锁定失败的商品集合
        List<LockStockDTO> unlockSkuList = skuLockList.stream().filter(item -> !item.getLocked()).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(unlockSkuList)) {
            // 恢复已被锁定的库存
            List<LockStockDTO> lockSkuList = skuLockList.stream().filter(LockStockDTO::getLocked).collect(Collectors.toList());
            lockSkuList.forEach(item ->
                    this.update(new LambdaUpdateWrapper<PmsSku>()
                            .eq(PmsSku::getId, item.getSkuId())
                            .setSql("locked_stock = locked_stock - " + item.getCount()))
            );
            // 提示订单哪些商品库存不足
            String ids = unlockSkuList.stream().map(sku -> sku.getSkuId().toString()).collect(Collectors.joining(","));
            return Result.failed("商品" + ids + "库存不足");
        }

        // 将锁定的商品保存至Redis中
        String orderToken = skuLockList.get(0).getOrderToken();
        redisTemplate.opsForValue().set(PmsConstants.LOCKED_STOCK_PREFIX + orderToken, JSONUtil.toJsonStr(skuLockList));
        return Result.success();
    }


    /**
     * 订单超时关单解锁库存
     */
    @Override
    public boolean unlockStock(String orderToken) {
        log.info("=======================订单超时未支付系统自动关单释放库存=======================");
        String json = redisTemplate.opsForValue().get(PmsConstants.LOCKED_STOCK_PREFIX + orderToken);
        log.info("释放库存信息：{}", json);
        if (StrUtil.isBlank(json)) {
            return true;
        }

        List<LockStockDTO> skuLockList = JSONUtil.toList(json, LockStockDTO.class);

        skuLockList.forEach(item ->
                this.update(new LambdaUpdateWrapper<PmsSku>()
                        .eq(PmsSku::getId, item.getSkuId())
                        .setSql("locked_stock = locked_stock - " + item.getCount()))
        );

        // 删除redis中锁定的库存
        redisTemplate.delete(PmsConstants.LOCKED_STOCK_PREFIX + orderToken);
        return true;
    }

    /**
     * 支付成功时扣减库存
     */
    @Override
    public boolean deductStock(String orderToken) {
        log.info("=======================支付成功扣减订单中商品库存=======================");
        String json = redisTemplate.opsForValue().get(PmsConstants.LOCKED_STOCK_PREFIX + orderToken);
        log.info("订单商品信息：{}", json);
        if (StrUtil.isBlank(json)) {
            return true;
        }

        List<LockStockDTO> skuLockList = JSONUtil.toList(json, LockStockDTO.class);

        skuLockList.forEach(item -> {
            boolean result = this.update(new LambdaUpdateWrapper<PmsSku>()
                    .eq(PmsSku::getId, item.getSkuId())
                    .setSql("stock = stock - " + item.getCount())  // 扣减库存
                    .setSql("locked_stock = locked_stock - " + item.getCount())
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
     * 获取商品库存信息
     *
     * @param skuId
     * @return
     */
    @Override
    public SkuInfoDTO getSkuInfo(Long skuId) {
        SkuInfoDTO skuInfo=   this.baseMapper.getSkuInfo(skuId);
        return skuInfo;
    }


   /* private final SeataTccSkuService seataTccSkuService;

    @Override
    @GlobalTransactional
    public Boolean lockStockTcc(List<LockStockDTO> skuLockList) {
        seataTccSkuService.prepareSkuLockList(null, skuLockList);
        String orderToken = skuLockList.get(0).getOrderToken();
        redisTemplate.opsForValue().set(PmsConstants.LOCKED_STOCK_PREFIX + orderToken, JSONUtil.toJsonStr(skuLockList));
        return true;
    }*/

}
