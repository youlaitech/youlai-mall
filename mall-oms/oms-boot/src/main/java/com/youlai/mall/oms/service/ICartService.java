package com.youlai.mall.oms.service;

import com.youlai.mall.oms.pojo.dto.CartItemDTO;

import java.util.List;

/**
 * 购物车业务接口
 */
public interface ICartService {



    List<CartItemDTO> listCartItemByMemberId(Long memberId);

    boolean deleteCart();

    boolean addCartItem(Long skuId);

    boolean updateCartItem(CartItemDTO cartItem);

    boolean removeCartItem(Long skuId);

    boolean removeCheckedItem();

    boolean checkAll(boolean checked);

}
