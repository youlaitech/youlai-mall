package com.youlai.mall.product.service;

import com.youlai.mall.product.model.entity.SpuDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 商品详情 服务类
 */
public interface SpuDetailService extends IService<SpuDetail> {

    /**
     * 保存商品详情
     *
     * @param spuId SPU ID
     * @param detail 商品详情
     */
    void saveSpuDetail(Long spuId, String detail);
}
