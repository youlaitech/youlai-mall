package com.youlai.mall.pms.converter;

import com.youlai.mall.pms.model.dto.SkuInfoDto;
import com.youlai.mall.pms.model.entity.PmsSku;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 商品对象转换器
 *
 * @author haoxr
 * @since 2022/6/11
 */
@Mapper(componentModel = "spring")
public interface SkuConverter {

    SkuInfoDto entity2SkuInfoDto(PmsSku entity);

    List<SkuInfoDto> entity2SkuInfoDto(List<PmsSku> list);
}
