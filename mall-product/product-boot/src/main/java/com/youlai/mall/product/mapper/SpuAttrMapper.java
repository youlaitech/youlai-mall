package com.youlai.mall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.product.model.bo.SpuAttrBO;
import com.youlai.mall.product.model.entity.SpuAttrEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * SPU属性持久层
 */
@Mapper
public interface SpuAttrMapper extends BaseMapper<SpuAttrEntity> {

    /**
     * 根据SPU ID查询商品属性列表
     *
     * @param spuId SPU ID
     * @return 商品属性列表
     */
    List<SpuAttrBO> listAttrsBySpuId(Long spuId);
}
