package com.youlai.mall.pms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.pms.common.constant.PmsConstants;
import com.youlai.mall.pms.mapper.PmsSkuMapper;
import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.pojo.dto.SkuLockDTO;
import com.youlai.mall.pms.service.IPmsSkuService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.youlai.mall.pms.common.constant.PmsConstants.PMS_STOCK_LOCK_PREFIX;

@Service
@Slf4j
@AllArgsConstructor
public class PmsSkuServiceImpl extends ServiceImpl<PmsSkuMapper, PmsSku> implements IPmsSkuService {


    private RedisTemplate redisTemplate;

    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean lockStock(List<SkuLockDTO> skuLockList) {

        if (CollectionUtil.isNotEmpty(skuLockList)) {
            throw new BizException("锁定商品为空");
        }

        // 锁定商品
        skuLockList.forEach(item -> {
            boolean result = this.update(new LambdaUpdateWrapper<PmsSku>()
                    .setSql("locked_stock = locked_stock + " + item.getCount())
                    .eq(PmsSku::getId, item.getSkuId())
                    .apply("stock >= locked_stock + {0}", item.getCount())
            );
            if (result) {
                item.setLocked(true);
            } else {
                item.setLocked(false);
            }
        });

        // 锁定失败的商品集合
        List<SkuLockDTO> unlockSkus = skuLockList.stream().filter(item -> !item.getLocked()).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(unlockSkus)) {
            // 恢复已被锁定的库存
            List<SkuLockDTO> lockSkus = skuLockList.stream().filter(SkuLockDTO::getLocked).collect(Collectors.toList());
            this.unlockStock(lockSkus);

            List<Long> ids = unlockSkus.stream().map(SkuLockDTO::getSkuId).collect(Collectors.toList());
            throw new BizException("商品" + ids.toString() + "库存不足");
        }

        // 将锁定的商品保存至Redis中
        String orderToken = skuLockList.get(0).getOrderToken();
        stringRedisTemplate.opsForValue().set(PMS_STOCK_LOCK_PREFIX+orderToken, JSONUtil.toJsonStr(skuLockList));
        return true;
    }

    @Override
    public boolean unlockStock(List<SkuLockDTO> skuList) {
        skuList.forEach(item -> {
            boolean result = this.update(new LambdaUpdateWrapper<PmsSku>()
                    .eq(PmsSku::getId, item.getSkuId())
                    .setSql("locked_stock = locked_stock - " + item.getCount())
            );
            if (!result) {
                throw new BizException("解锁库存失败，库存ID:" + item.getSkuId() + "，数量:" + item.getCount());
            }
        });
        return true;
    }


    @Override
    public boolean deductStock(List<SkuLockDTO> inventories) {
        inventories.forEach(item -> {
            boolean result = this.update(new LambdaUpdateWrapper<PmsSku>()
                    .eq(PmsSku::getId, item.getSkuId())
                    .setSql("locked_stock = locked_stock - " + item.getCount())
                    .setSql("stock = stock - " + item.getCount())
            );
            if (!result) {
                throw new BizException("扣减库存失败");
            }
        });
        return false;
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
        Object cacheVal = redisTemplate.opsForValue().get(PMS_STOCK_LOCK_PREFIX + id);
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
            redisTemplate.opsForValue().set(PmsConstants.PMS_STOCK_LOCK_PREFIX + id, stock);
        }

        return stock;

    }

    @Override
    public List<SkuDTO> listBySkuIds(List<Long> ids) {
        return this.baseMapper.listBySkuIds(ids);
    }


}
