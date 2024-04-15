package com.youlai.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.pms.model.bo.SkuBO;
import com.youlai.mall.pms.model.dto.SkuInfoDto;
import com.youlai.mall.pms.model.entity.Sku;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SkuMapper extends BaseMapper<Sku> {

    /**
     * 根据SKU的ID获取对应的商品库存单元（SKU）信息。
     *
     * @param skuId 商品库存单元的唯一标识。
     * @return 返回一个SkuInfoDto对象，包含了SKU的详细信息。
     */
    SkuInfoDto getSkuInfo(Long skuId);

    /**
     * 根据一组SKU的ID列表获取对应的商品库存信息列表。
     *
     * @param skuIds 商品库存单元ID的列表。
     * @return 返回一个SkuInfoBo列表，包含了每个SKU的库存信息。
     */
    List<SkuBO> listSkuInfos(List<Long> skuIds);
}
