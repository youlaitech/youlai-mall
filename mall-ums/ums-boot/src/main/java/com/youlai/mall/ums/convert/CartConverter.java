
package com.youlai.mall.ums.convert;

import com.youlai.mall.pms.model.dto.SkuInfoDTO;
import com.youlai.mall.ums.model.vo.CartItemVo;
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
    CartItemVo sku2CartItem(SkuInfoDTO skuInfo);

}