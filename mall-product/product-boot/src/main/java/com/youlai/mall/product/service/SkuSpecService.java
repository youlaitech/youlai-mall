package com.youlai.mall.product.service;

import com.youlai.mall.product.model.entity.SkuSpec;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.form.SpuForm;

import java.util.List;

/**
 * SKU 规格接口层
 *
 * @author Ray.Hao
 * @since 2024-04-14
 */
public interface SkuSpecService extends IService<SkuSpec> {

    /**
     * 保存商品规格值
     *
     * @param id    SKU ID
     * @param specList 规格列表
     */
    void saveSkuSpecs(Long id, List<SpuForm.Spec> specList);
}
