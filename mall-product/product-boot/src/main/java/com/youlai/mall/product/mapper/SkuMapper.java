package com.youlai.mall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.product.model.bo.SkuBO;
import com.youlai.mall.product.model.dto.SkuDTO;
import com.youlai.mall.product.model.entity.Sku;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SkuMapper extends BaseMapper<Sku> {

    /**
     * 获取 SKU
     *
     * @param skuId  SKU ID
     */
    SkuBO getSkuById(Long skuId);

    /**
     * 根据 SKU ID 集合获取 SKU 列表
     *
     * @param skuIds SKU ID 集合
     */
    List<SkuBO> listSkusByIds(List<Long> skuIds);

    /**
     * 根据 SPU ID 获取 SKU 列表
     *
     * @param spuId SPU ID
     */
    List<SkuBO> listSkusBySpuId(Long spuId);
}
