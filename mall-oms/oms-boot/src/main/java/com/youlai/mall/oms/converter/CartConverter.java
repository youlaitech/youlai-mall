
package com.youlai.mall.oms.converter;

import com.youlai.mall.oms.model.dto.CartItemDto;
import com.youlai.mall.pms.model.dto.SkuInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 购物车对象转化器
 *
 * @author haoxr
 * @since 2.0.0
 */
@Mapper(componentModel = "spring")
public interface CartConverter {

    @Mappings({
            @Mapping(target = "skuId", source = "id"),
    })
    CartItemDto sku2CartItem(SkuInfoDTO skuInfo);

}