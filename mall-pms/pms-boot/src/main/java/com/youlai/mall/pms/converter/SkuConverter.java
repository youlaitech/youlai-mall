package com.youlai.mall.pms.converter;

import com.youlai.mall.pms.pojo.dto.SkuInfoDTO;
import com.youlai.mall.pms.pojo.entity.PmsSku;
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

    SkuInfoDTO entity2SkuInfoDto(PmsSku entity);

    List<SkuInfoDTO> entity2SkuInfoDto(List<PmsSku> list);
}
