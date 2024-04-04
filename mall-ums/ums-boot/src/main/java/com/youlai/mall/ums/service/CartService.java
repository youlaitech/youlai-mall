package com.youlai.mall.ums.service;


import com.youlai.mall.ums.model.vo.CartItemVo;

import java.util.List;

/**
 * 购物车接口
 *
 * @author Ray Hao
 * @since 2024/4/4
 */
public interface CartService {

    /**
     * 获取用户的购物车列表
     *
     * @param memberId 会员ID
     * @return 购物车列表
     */
    List<CartItemVo> listCartItems(Long memberId);

    /**
     * 清空当前会员的购物车
     */
    void clearCart();

    /**
     * 添加商品至购物车
     *
     * @param skuId 商品库存ID
     * @param quantity 商品数量
     */
    void addCartItem(Long skuId,Integer quantity);


    /**
     * 从购物车中移除指定商品
     */
    void removeCartItem(Long skuId);

    void removeCheckedItem();

    void toggleCheckAllItems(boolean checked);

}
