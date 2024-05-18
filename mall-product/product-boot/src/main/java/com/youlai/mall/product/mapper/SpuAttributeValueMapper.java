package com.youlai.mall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.product.model.entity.SpuAttributeValue;
import com.youlai.mall.product.model.form.SpuForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * SPU属性值 Mapper
 */
@Mapper
public interface SpuAttributeValueMapper extends BaseMapper<SpuAttributeValue> {

    /**
     * 根据SPU ID查询属性组&属性列表
     *
     * @param spuId SPU ID
     * @return 属性列表
     */
    List<SpuForm.AttributeGroup> listAttributeGroupsBySpuId(Long spuId);
}
