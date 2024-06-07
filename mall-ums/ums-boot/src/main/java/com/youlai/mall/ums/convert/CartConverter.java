
package com.youlai.mall.ums.convert;

import com.youlai.mall.ums.model.dto.CartItemCache;
import com.youlai.mall.ums.dto.CartItemDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 购物车对象转化器
 *
 * @author Ray
 * @since 2.0.0
 */
@Mapper(componentModel = "spring")
public interface CartConverter {


    CartItemDTO cartItemCacheToVo(CartItemCache cache);

    List<CartItemDTO> cartItemCacheToVo(List<CartItemCache> caches);



}