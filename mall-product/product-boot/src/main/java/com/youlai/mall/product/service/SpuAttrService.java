package com.youlai.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.bo.SpuAttrBO;
import com.youlai.mall.product.model.entity.SpuAttr;
import com.youlai.mall.product.model.form.SpuForm;

import java.util.List;

/**
 * 商品属性 接口层
 *
 * @author Ray.Hao
 * @since 2024/04/24
 */
public interface SpuAttrService extends IService<SpuAttr> {

    /**
     * 保存商品属性
     *
     * @param spuId      SPU ID
     * @param attrList   属性列表
     */
    void saveSpuAttrs(Long spuId,  List<SpuForm.AttrValue> attrList);

    /**
     * 判断属性是否有商品引用
     *
     * @param attrId 属性ID
     */
    boolean isAttrReferenced(Long attrId);

    /**
     * 根据SPU ID查询商品属性列表
     *
     * @param spuId SPU ID
     * @return 商品属性列表
     */
    List<SpuAttrBO> listAttrsBySpuId(Long spuId);
}
