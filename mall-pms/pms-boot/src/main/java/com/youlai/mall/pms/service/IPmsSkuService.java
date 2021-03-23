package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.pojo.dto.SkuLockDTO;

import java.util.List;

public interface IPmsSkuService extends IService<PmsSku> {

    /**
     * 锁定库存
     */
    boolean lockStock(List<SkuLockDTO> list);

    /**
     * 解锁库存
     */
    boolean unlockStock(String orderToken);

    /**
     * 扣减库存
     */
    boolean deductStock(String orderToken);

    /**
     * 获取商品库存数量
     */
    Integer getStockById(Long id);

    SkuDTO getSkuById(Long id);
}
