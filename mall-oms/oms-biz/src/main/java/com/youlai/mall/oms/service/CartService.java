package com.youlai.mall.oms.service;

import com.youlai.mall.oms.bo.CartItemBo;
import com.youlai.mall.oms.bo.CartItemCheckBo;
import com.youlai.mall.oms.pojo.vo.CartVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 购物车业务接口
 */
public interface CartService {

    /**
     * 添加商品到购物车
     * @param skuId 商品模型
     */
    void save(String skuId) throws ExecutionException, InterruptedException;

    /**
     * 修改购物车商品数量
     * @param cartItemBo
     */
    void update(CartItemBo cartItemBo);

    /**
     * 修改购物车中商品是否选中状态
     * @param cartItemCheckBo
     */
    void check(CartItemCheckBo cartItemCheckBo);

    /**
     * 全选/全不选购物车
     * @param check
     */
    void checkAll(Integer check);

    /**
     * 删除购物车中的商品
     * @param skuId 商品id
     */
    void delete(Long skuId);

    /**
     * 批量删除购物车中的商品
     * @param skuIds 商品id集合
     */
    void deleteBatch(List<String> skuIds);

    /**
     * 查询购物车详情
     * @return
     */
    CartVo detail();

    /**
     * 清空购物车
     */
    void clear();


}
