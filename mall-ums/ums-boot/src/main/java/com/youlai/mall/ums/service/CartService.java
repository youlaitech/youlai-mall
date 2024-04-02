package com.youlai.mall.ums.service;


import com.youlai.mall.ums.model.vo.CartItemVo;

import java.util.List;

/**
 * 购物车业务接口
 *
 * @author haoxr
 * @date 2022/11/13
 */
public interface CartService {

    List<CartItemVo> listCartItems(Long memberId);

    boolean deleteCart();

    boolean addCartItem(Long skuId);


    boolean removeCartItem(Long skuId);

    boolean removeCheckedItem();

    boolean checkAll(boolean checked);

}
