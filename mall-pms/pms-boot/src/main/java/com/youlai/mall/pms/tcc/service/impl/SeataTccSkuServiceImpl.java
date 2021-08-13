package com.youlai.mall.pms.tcc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.pms.common.constant.PmsConstants;
import com.youlai.mall.pms.pojo.dto.app.LockStockDTO;
import com.youlai.mall.pms.pojo.entity.PmsSku;
import com.youlai.mall.pms.service.IPmsSkuService;
import com.youlai.mall.pms.tcc.idempotent.IdempotentUtil;
import com.youlai.mall.pms.tcc.service.SeataTccSkuService;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Component
public class SeataTccSkuServiceImpl implements SeataTccSkuService {

    @Autowired
    private IPmsSkuService iPmsSkuService;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    @Transactional
    public boolean prepareSkuLockList(BusinessActionContext businessActionContext, List<LockStockDTO> skuLockList) {

        if (Objects.nonNull(IdempotentUtil.getMarker(getClass(), businessActionContext.getXid()))) {
            return true;
        }
        if (CollectionUtil.isEmpty(skuLockList)) {
            throw new BizException("锁定的商品列表为空");
        }
        // 锁定商品
        skuLockList.forEach(item -> {
            RLock lock = redissonClient.getLock(PmsConstants.LOCK_SKU_PREFIX + item.getSkuId()); // 获取商品的分布式锁
            lock.lock();
            boolean result = iPmsSkuService.update(new LambdaUpdateWrapper<PmsSku>()
                    .setSql("locked_stock = locked_stock + " + item.getCount())
                    .eq(PmsSku::getId, item.getSkuId())
                    .apply("stock - locked_stock >= {0}", item.getCount())
            );
            item.setLocked(result);
            lock.unlock();
        });
        // 锁定失败的商品集合
        List<LockStockDTO> unlockSkuList = skuLockList.stream().filter(item -> !item.getLocked()).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(unlockSkuList)) {
            // 恢复已被锁定的库存
            List<LockStockDTO> lockSkuList = skuLockList.stream().filter(LockStockDTO::getLocked).collect(Collectors.toList());
            lockSkuList.forEach(item ->
                    iPmsSkuService.update(new LambdaUpdateWrapper<PmsSku>()
                            .eq(PmsSku::getId, item.getSkuId())
                            .setSql("locked_stock = locked_stock - " + item.getCount()))
            );
            // 提示订单哪些商品库存不足
            List<Long> ids = unlockSkuList.stream().map(LockStockDTO::getSkuId).collect(Collectors.toList());
            throw new BizException("商品" + ids.toString() + "库存不足");
        }
        IdempotentUtil.addMarker(getClass(), businessActionContext.getXid(), System.currentTimeMillis());
        return true;
    }

    @Transactional
    @Override
    public boolean commitSkuLockList(BusinessActionContext businessActionContext) {
        if (Objects.isNull(IdempotentUtil.getMarker(getClass(), businessActionContext.getXid()))) {
            return true;
        }
        IdempotentUtil.removeMarker(getClass(), businessActionContext.getXid());
        return true;
    }

    @Transactional
    @Override
    public boolean rollbackSkuLockList(BusinessActionContext businessActionContext) {

        if (Objects.isNull(IdempotentUtil.getMarker(getClass(), businessActionContext.getXid()))) {
            return true;
        }
        JSONArray jsonObjectList = (JSONArray) businessActionContext.getActionContext("skuLockList");

        List<LockStockDTO> skuLockList = JSONUtil.toList(jsonObjectList,LockStockDTO.class);
        skuLockList.forEach(item ->
                iPmsSkuService.update(new LambdaUpdateWrapper<PmsSku>()
                        .eq(PmsSku::getId, item.getSkuId())
                        .setSql("locked_stock = locked_stock - " + item.getCount()))
        );
        IdempotentUtil.removeMarker(getClass(), businessActionContext.getXid());
        return true;
    }
}
