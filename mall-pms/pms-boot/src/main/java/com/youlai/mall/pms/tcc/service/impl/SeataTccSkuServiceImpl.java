package com.youlai.mall.pms.tcc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.pms.pojo.dto.SkuLockDTO;
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

import static com.youlai.mall.pms.common.constant.PmsConstants.LOCK_SKU_PREFIX;

@Slf4j
@Component
public class SeataTccSkuServiceImpl implements SeataTccSkuService {

    @Autowired
    private IPmsSkuService iPmsSkuService;

    @Autowired
    private RedissonClient redissonClient;

    @Transactional
    @Override
    public boolean prepareSkuLockList(BusinessActionContext businessActionContext, List<SkuLockDTO> skuLockList) {
        log.info("=======================创建订单，开始锁定商品库存=======================");
        //防幂等
        if (Objects.nonNull(IdempotentUtil.getMarker(getClass(), businessActionContext.getXid()))) {
            log.info("已执行过try阶段");
            return true;
        }
        log.info("锁定商品信息：{}", skuLockList.toString());
        if (CollectionUtil.isEmpty(skuLockList)) {
            throw new BizException("锁定的商品列表为空");
        }
        // 锁定商品
        skuLockList.forEach(item -> {
            RLock lock = redissonClient.getLock(LOCK_SKU_PREFIX + item.getSkuId()); // 获取商品的分布式锁
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
        List<SkuLockDTO> unlockSkuList = skuLockList.stream().filter(item -> !item.getLocked()).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(unlockSkuList)) {
            // 恢复已被锁定的库存
            List<SkuLockDTO> lockSkuList = skuLockList.stream().filter(SkuLockDTO::getLocked).collect(Collectors.toList());
            lockSkuList.forEach(item ->
                    iPmsSkuService.update(new LambdaUpdateWrapper<PmsSku>()
                            .eq(PmsSku::getId, item.getSkuId())
                            .setSql("locked_stock = locked_stock - " + item.getCount()))
            );
            // 提示订单哪些商品库存不足
            List<Long> ids = unlockSkuList.stream().map(SkuLockDTO::getSkuId).collect(Collectors.toList());
            throw new BizException("商品" + ids.toString() + "库存不足");
        }
        IdempotentUtil.addMarker(getClass(), businessActionContext.getXid(), System.currentTimeMillis());
        return true;
    }

    @Transactional
    @Override
    public boolean commitSkuLockList(BusinessActionContext businessActionContext) {
        log.info("=====================commitSkuLockList 成功=========================");
        if (Objects.isNull(IdempotentUtil.getMarker(getClass(), businessActionContext.getXid()))) {
            log.info(businessActionContext.getXid() + ": 已执行过commit阶段");
            return true;
        }
        IdempotentUtil.removeMarker(getClass(), businessActionContext.getXid());
        return true;
    }

    @Transactional
    @Override
    public boolean rollbackSkuLockList(BusinessActionContext businessActionContext) {
        log.info("======================rollbackSkuLockList========================");
        if (Objects.isNull(IdempotentUtil.getMarker(getClass(), businessActionContext.getXid()))) {
            log.info(businessActionContext.getXid() + ": 已执行过rollback阶段");
            return true;
        }
        JSONArray jsonObjectList = (JSONArray) businessActionContext.getActionContext("skuLockList");
        List<SkuLockDTO> skuLockList = JSONObject.parseArray(jsonObjectList.toJSONString(), SkuLockDTO.class);
        skuLockList.forEach(item ->
                iPmsSkuService.update(new LambdaUpdateWrapper<PmsSku>()
                        .eq(PmsSku::getId, item.getSkuId())
                        .setSql("locked_stock = locked_stock - " + item.getCount()))
        );
        IdempotentUtil.removeMarker(getClass(), businessActionContext.getXid());
        return true;
    }
}
