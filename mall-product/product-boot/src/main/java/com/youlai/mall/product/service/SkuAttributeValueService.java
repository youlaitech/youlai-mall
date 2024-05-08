package com.youlai.mall.product.service;

import com.youlai.mall.product.model.entity.SkuAttributeValue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.form.SpuForm;

import java.util.List;

/**
 * SKU销售属性值 服务类
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
public interface SkuAttributeValueService extends IService<SkuAttributeValue> {

    /**
     * 保存销售属性值
     *
     * @param id    SKU ID
     * @param saleAttributeValues 销售属性值列表
     */
    void saveSaleAttributeValues(Long id, List<SpuForm.AttributeValue> saleAttributeValues);
}
