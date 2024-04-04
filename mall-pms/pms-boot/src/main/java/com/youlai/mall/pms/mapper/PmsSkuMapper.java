package com.youlai.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.pms.model.dto.SkuInfoDto;
import com.youlai.mall.pms.model.entity.PmsSku;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PmsSkuMapper extends BaseMapper<PmsSku> {

    /**
     * 获取商品库存单元信息
     *
     * @param skuId
     * @return
     */
    SkuInfoDto getSkuInfo(Long skuId);
}
