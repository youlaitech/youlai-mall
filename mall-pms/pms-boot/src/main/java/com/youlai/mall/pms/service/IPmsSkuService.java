package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.entity.PmsSku;
import com.youlai.mall.pms.pojo.dto.app.SkuDTO;
import com.youlai.mall.pms.pojo.dto.app.LockStockDTO;

import java.util.List;

public interface IPmsSkuService extends IService<PmsSku> {

    /**
     * 锁定库存
     */
    Result lockStock(List<LockStockDTO> list);

    /**
     * 锁定库存
     */
    Boolean lockStockTcc(List<LockStockDTO> list);

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
