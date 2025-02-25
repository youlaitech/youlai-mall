package com.youlai.mall.member.service;


import com.youlai.mall.member.dto.CartItemDTO;

import java.util.List;

/**
 * 购物车接口
 *
 * @author Ray.Hao
 * @since 2024/4/4
 */
public interface CartService {

    /**
     * 获取用户的购物车列表
     *
     * @param memberId 会员ID
     * @return 购物车列表
     */
    List<CartItemDTO> listCartItems(Long memberId);

    /**
     * 清空当前会员的购物车
     */
    void clearCart();

    /**
     * 添加商品至购物车
     *
     * @param skuId    商品库存ID
     * @param quantity 商品数量
     */
    void addCartItem(Long skuId, Integer quantity);


    /**
     * 从购物车中移除指定商品
     */
    void removeCartItem(Long skuId);

    /**
     * 删除选中的购物车商品
     * <p>
     * 订单支付成功，删除购物车中已选中的商品
     */
    void removeCheckedCartItems(Long memberId);

    /**
     * 切换购物车商品选中状态(全选/全不选)
     */
    void toggleCheckAllItems(boolean checked);

}
