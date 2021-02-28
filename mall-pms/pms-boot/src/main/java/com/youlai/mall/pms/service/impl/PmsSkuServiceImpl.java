package com.youlai.mall.pms.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.pms.common.RedisConstants;
import com.youlai.mall.pms.mapper.PmsSkuMapper;
import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.vo.SkuInfoVO;
import com.youlai.mall.pms.pojo.vo.SkuStockVO;
import com.youlai.mall.pms.pojo.vo.WareSkuStockVO;
import com.youlai.mall.pms.service.IPmsSkuService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PmsSkuServiceImpl extends ServiceImpl<PmsSkuMapper, PmsSku> implements IPmsSkuService {


    private RedisTemplate redisTemplate;

    @Override
    public List<SkuInfoVO> getSkuInfoByIds(List<String> skuIds) {
        log.info("批量获取商品详情，skuIds:{}", skuIds);
        return baseMapper.getSkuInfoByIds(skuIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean lockStock(WareSkuStockVO skuStockVO) {
        log.info("订单锁定商品库存，商品信息：{}", skuStockVO.getItems());
        for (SkuStockVO item : skuStockVO.getItems()) {
            Long result = baseMapper.lockStock(item.getSkuId(), item.getNumber());
            if (result == 0) {
                log.info("商品库存锁定失败，商品id:{}，数量:{}", item.getSkuId(), item.getNumber());
                throw new BizException("商品库存锁定失败，商品id:" + item.getSkuId() + "，数量:" + item.getNumber());
            }
        }
        return true;
    }

    @Override
    public boolean releaseStock(WareSkuStockVO skuStockVO) {
        log.info("订单关闭释放商品库存，商品信息：{}", skuStockVO.getItems());
        for (SkuStockVO item : skuStockVO.getItems()) {
            Long result = baseMapper.releaseStock(item.getSkuId(), item.getNumber());
            if (result == 0) {
                log.info("商品库存释放失败，商品id:{}，数量:{}", item.getSkuId(), item.getNumber());
                throw new BizException("商品库存释放失败，商品id:" + item.getSkuId() + "，数量:" + item.getNumber());
            }
        }
        return true;
    }


    /**
     * Cache-Aside pattern 缓存、数据库读写模式
     * 1. 读取数据，先读缓存，没有就去读数据库，然后将结果写入缓存
     * 2. 写入数据，先更新数据库，再删除缓存
     * @param skuId
     * @return
     */
    @Override
    public Integer getInventoryBySkuId(Long skuId) {
        Integer inventory = 0;
        // 读->缓存
        Object cacheVal = redisTemplate.opsForValue().get(RedisConstants.PRODUCT_INVENTORY_PREFIX + skuId);
        if (cacheVal != null) {
            inventory = Convert.toInt(cacheVal);
            return inventory;
        }

        // 读->数据库
        PmsSku pmsInventory = this.getOne(new LambdaQueryWrapper<PmsSku>()
                .eq(PmsSku::getId, skuId)
                .select(PmsSku::getStock));

        if (pmsInventory != null) {
            inventory = pmsInventory.getStock();
            // 写->缓存
            redisTemplate.opsForValue().set(RedisConstants.PRODUCT_INVENTORY_PREFIX + skuId, inventory);
        }

        return inventory;

    }
}
