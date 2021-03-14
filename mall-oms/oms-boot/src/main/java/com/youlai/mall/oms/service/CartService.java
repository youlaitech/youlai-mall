package com.youlai.mall.oms.service;

import com.youlai.mall.oms.pojo.vo.CartVO;

/**
 * 购物车业务接口
 */
public interface CartService {

    /**
     * 添加商品到购物车
     */
    void addCartItem(Long skuId);

    /**
     * 修改购物车商品数量
     */
    void updateCartItem(Long skuId,Integer num,Boolean checked);


    /**
     * 全选/全不选购物车
     */
    void checkAll(Boolean checked);

    /**
     * 批量删除购物车中的商品
     */
    void deleteCartItem(Long skuId);

    /**
     * 查询购物车详情
     */
    CartVO getCart();

    /**
     * 清空购物车
     */
    void deleteCart();

    /**
     * 清空购物车中已选择商品
     */
    void cleanSelected();


}
