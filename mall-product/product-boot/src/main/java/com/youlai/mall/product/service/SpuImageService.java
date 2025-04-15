package com.youlai.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.entity.SpuImage;
import com.youlai.mall.product.model.form.SpuForm;

import java.util.List;

/**
 * 商品图片接口
 *
 * @author Ray.Hao
 * @since 2024-04-14
 */
public interface SpuImageService extends IService<SpuImage> {

    /**
     * 保存商品图片
     *
     * @param spuId 商品ID
     * @param imgUrls 商品图册
     * @return
     */
    void saveSpuImages(Long spuId, List<String> imgUrls);
}
