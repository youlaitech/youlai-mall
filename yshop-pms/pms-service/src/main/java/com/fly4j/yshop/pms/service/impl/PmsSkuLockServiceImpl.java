package com.fly4j.yshop.pms.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.yshop.pms.mapper.PmsSkuLockMapper;
import com.fly4j.yshop.pms.pojo.entity.PmsSkuLock;
import com.fly4j.yshop.pms.pojo.vo.SkuLockVO;
import com.fly4j.yshop.pms.service.IPmsSkuLockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class PmsSkuLockServiceImpl extends ServiceImpl<PmsSkuLockMapper, PmsSkuLock> implements IPmsSkuLockService {
    @Resource
    private PmsSkuLockMapper pmsSkuLockMapper;

    @Override
    public List<PmsSkuLock> getAllCanLocked(SkuLockVO skuLockVO) {
        return pmsSkuLockMapper.getAllCanLocked(skuLockVO);
    }

    @Override
    public long lockSku(Long id, Integer quantity) {
        return pmsSkuLockMapper.lockSku(id,quantity);
    }

    @Override
    public void unLockSku(SkuLockVO skuLockVO) {
        pmsSkuLockMapper.unLockSku(skuLockVO);
    }
}
