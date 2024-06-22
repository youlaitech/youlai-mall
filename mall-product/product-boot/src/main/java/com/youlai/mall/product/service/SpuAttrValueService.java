package com.youlai.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.entity.SpuAttrValue;
import com.youlai.mall.product.model.form.SpuForm;

import java.util.List;

/**
 * 商品属性 接口层
 *
 * @author Ray Hao
 * @since 2024/04/24
 */
public interface SpuAttrValueService extends IService<SpuAttrValue> {

    /**
     * 保存商品属性
     *
     * @param spuId      SPU ID
     * @param attrValues 属性列表
     */
    void saveAttributeValues(Long spuId, List<SpuForm.AttrValue> attrValues);

    /**
     * 判断属性是否有商品引用
     *
     * @param attrId 属性ID
     */
    boolean isAttrReferenced(Long attrId);
}
