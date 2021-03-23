package com.youlai.mall.pms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.pms.mapper.PmsSkuMapper;
import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.pojo.dto.SkuLockDTO;
import com.youlai.mall.pms.service.IPmsSkuService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.youlai.mall.pms.common.constant.PmsConstants.LOCKED_STOCK_PREFIX;
import static com.youlai.mall.pms.common.constant.PmsConstants.LOCK_SKU_PREFIX;

@Service
@Slf4j
@AllArgsConstructor
public class PmsSkuServiceImpl extends ServiceImpl<PmsSkuMapper, PmsSku> implements IPmsSkuService {

    private StringRedisTemplate redisTemplate;

    private RedissonClient redissonClient;

    /**
     * 创建订单时锁定库存
     */
    @Override
    public boolean lockStock(List<SkuLockDTO> skuLockList) {
        log.info("=======================创建订单，开始锁定商品库存=======================");
        log.info("锁定商品信息：{}", skuLockList.toString());
        if (CollectionUtil.isEmpty(skuLockList)) {
            throw new BizException("锁定的商品列表为空");
        }

        // 锁定商品
        skuLockList.forEach(item -> {
            RLock lock = redissonClient.getLock(LOCK_SKU_PREFIX + item.getSkuId()); // 获取商品的分布式锁
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
        List<SkuLockDTO> unlockSkuList = skuLockList.stream().filter(item -> !item.getLocked()).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(unlockSkuList)) {
            // 恢复已被锁定的库存
            List<SkuLockDTO> lockSkuList = skuLockList.stream().filter(SkuLockDTO::getLocked).collect(Collectors.toList());
            lockSkuList.forEach(item ->
                    this.update(new LambdaUpdateWrapper<PmsSku>()
                            .eq(PmsSku::getId, item.getSkuId())
                            .setSql("locked_stock = locked_stock - " + item.getCount()))
            );
            // 提示订单哪些商品库存不足
            List<Long> ids = unlockSkuList.stream().map(SkuLockDTO::getSkuId).collect(Collectors.toList());
            throw new BizException("商品" + ids.toString() + "库存不足");
        }

        // 将锁定的商品保存至Redis中
        String orderToken = skuLockList.get(0).getOrderToken();
        redisTemplate.opsForValue().set(LOCKED_STOCK_PREFIX + orderToken, JSONUtil.toJsonStr(skuLockList));
        return true;
    }

    /**
     * 订单超时关单解锁库存
     */
    @Override
    public boolean unlockStock(String orderToken) {
        log.info("=======================订单超时未支付系统自动关单释放库存=======================");
        String json = redisTemplate.opsForValue().get(LOCKED_STOCK_PREFIX + orderToken);
        log.info("释放库存信息：{}", json);
        if (StrUtil.isBlank(json)) {
            return true;
        }

        List<SkuLockDTO> skuLockList = JSONUtil.toList(json, SkuLockDTO.class);

        skuLockList.forEach(item ->
                this.update(new LambdaUpdateWrapper<PmsSku>()
                        .eq(PmsSku::getId, item.getSkuId())
                        .setSql("locked_stock = locked_stock - " + item.getCount()))
        );

        // 删除redis中锁定的库存
        redisTemplate.delete(LOCKED_STOCK_PREFIX + orderToken);
        return true;
    }

    /**
     * 支付成功时扣减库存
     */
    @Override
    public boolean deductStock(String orderToken) {
        log.info("=======================支付成功扣减订单中商品库存=======================");
        String json = redisTemplate.opsForValue().get(LOCKED_STOCK_PREFIX + orderToken);
        log.info("订单商品信息：{}", json);
        if (StrUtil.isBlank(json)) {
            return true;
        }

        List<SkuLockDTO> skuLockList = JSONUtil.toList(json, SkuLockDTO.class);

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
        redisTemplate.delete(LOCKED_STOCK_PREFIX + orderToken);
        return true;
    }


    /**
     * Cache-Aside pattern 缓存、数据库读写模式
     * 1. 读取数据，先读缓存，没有就去读数据库，然后将结果写入缓存
     * 2. 写入数据，先更新数据库，再删除缓存
     *
     * @param id 库存ID
     * @return
     */
    @Override
    public Integer getStockById(Long id) {
        Integer stock = 0;
        // 读->缓存
        Object cacheVal = redisTemplate.opsForValue().get(LOCKED_STOCK_PREFIX + id);
        if (cacheVal != null) {
            stock = Convert.toInt(cacheVal);
            return stock;
        }

        // 读->数据库
        PmsSku pmsSku = this.getOne(new LambdaQueryWrapper<PmsSku>()
                .eq(PmsSku::getId, id)
                .select(PmsSku::getStock));

        if (pmsSku != null) {
            stock = pmsSku.getStock();
            // 写->缓存
            redisTemplate.opsForValue().set(LOCKED_STOCK_PREFIX + id, String.valueOf(stock));
        }

        return stock;
    }

    @Override
    public SkuDTO getSkuById(Long id) {
        return this.baseMapper.getSkuById(id);
    }

}
