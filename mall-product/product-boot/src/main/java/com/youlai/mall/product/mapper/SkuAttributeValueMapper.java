package com.youlai.mall.product.mapper;

import com.youlai.mall.product.model.entity.SkuAttributeValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * SKU 属性值 Mapper 接口
 *
 * @author Ray Hao
 * @since 2024/04/14
 */

@Mapper
public interface SkuAttributeValueMapper extends BaseMapper<SkuAttributeValue> {

    /**
     * 根据SKU ID获取销售属性值列表
     *
     * @param skuId SKU ID
     * @return 销售属性值列表
     */
    List<SkuAttributeValue> listAttributesBySkuId(Long skuId);

}