package com.youlai.mall.product.service;

import com.youlai.mall.product.model.entity.SkuSpec;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.form.SpuForm;

import java.util.List;

/**
 * SKU销售属性值 服务类
 *
 * @author Ray.Hao
 * @since 2024-04-14
 */
public interface SkuSpecValueService extends IService<SkuSpec> {

    /**
     * 保存商品规格值
     *
     * @param id    SKU ID
     * @param specValues 规格值列表
     */
    void specSpecValues(Long id, List<SpuForm.SpecValue> specValues);
}
