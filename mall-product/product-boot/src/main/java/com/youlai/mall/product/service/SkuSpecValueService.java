package com.youlai.mall.product.service;

import com.youlai.mall.product.model.entity.SkuSpecValue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.form.SpuForm;

import java.util.List;

/**
 * SKU规格值 服务类
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
public interface SkuSpecValueService extends IService<SkuSpecValue> {

    void saveSkuSpecValues(Long id, List<SpuForm.Attribute> specList);
}
