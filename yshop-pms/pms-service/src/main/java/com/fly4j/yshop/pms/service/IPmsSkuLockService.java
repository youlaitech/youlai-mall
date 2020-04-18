package com.fly4j.yshop.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.yshop.pms.pojo.entity.PmsSkuLock;
import com.fly4j.yshop.pms.pojo.vo.SkuLockVO;

import java.util.List;

public interface IPmsSkuLockService extends IService<PmsSkuLock> {
    List<PmsSkuLock> getAllCanLocked(SkuLockVO skuLockVO);

    long lockSku(Long id, Integer quantity);

    void unLockSku(SkuLockVO skuLock);
}
