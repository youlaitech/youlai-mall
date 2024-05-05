package com.youlai.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.entity.SpuAttributeValue;
import com.youlai.mall.product.model.form.SpuForm;

import java.util.List;

/**
 * 商品属性 接口层
 *
 * @author Ray Hao
 * @since 2024/04/24
 */
public interface SpuAttributeService extends IService<SpuAttributeValue> {

    /**
     * 保存商品属性
     *
     * @param spuId SPU ID
     * @param attributeList 属性列表
     */
    void saveSpuAttributes(Long spuId, List<SpuForm.Attribute> attributeList);

}
