
package com.youlai.mall.oms.converter;

import com.youlai.mall.oms.model.dto.CartItemDTO;
import com.youlai.mall.pms.pojo.dto.SkuInfoDTO;
import org.mapstruct.Mapper;

/**
 * 购物车对象转化器
 *
 * @author haoxr
 * @since 2.0.0
 */
@Mapper(componentModel = "spring")
public interface CartConverter {

    CartItemDTO sku2CartItem(SkuInfoDTO skuInfo);

}