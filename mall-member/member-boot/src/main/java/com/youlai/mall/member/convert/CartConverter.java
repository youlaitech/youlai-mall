
package com.youlai.mall.member.convert;

import com.youlai.mall.member.model.cache.CartItemCache;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 购物车对象转化器
 *
 * @author Ray.Hao
 * @since 2.0.0
 */
@Mapper(componentModel = "spring")
public interface CartConverter {


    com.youlai.mall.member.dto.CartItemDTO convertToDto(CartItemCache cache);

    List<com.youlai.mall.member.dto.CartItemDTO> convertToDto(List<CartItemCache> caches);

}