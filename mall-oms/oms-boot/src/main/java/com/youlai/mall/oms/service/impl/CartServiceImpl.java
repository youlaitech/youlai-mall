package com.youlai.mall.oms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.youlai.common.result.ResultCode;
import com.youlai.common.web.exception.BizException;
import com.youlai.common.web.util.JwtUtils;
import com.youlai.common.web.util.MemberUtils;
import com.youlai.mall.oms.constant.OmsConstants;
import com.youlai.mall.oms.pojo.dto.CartItemDTO;
import com.youlai.mall.oms.service.ICartService;
import com.youlai.mall.pms.api.SkuFeignClient;
import com.youlai.mall.pms.pojo.dto.SkuInfoDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * 购物车模块
 * <p>
 * 技术点：BoundHashOperations
 * 数据格式：
 * -- key <----> 购物车
 * -- hKey:value <----> 商品1
 * -- hKey:value <----> 商品2
 * -- hKey:value <----> 商品3
 */
@Service
@Slf4j
@AllArgsConstructor
public class CartServiceImpl implements ICartService {

    private RedisTemplate redisTemplate;
    private SkuFeignClient skuFeignService;

    @Override
    public List<CartItemDTO> listCartItemByMemberId(Long memberId) {
        BoundHashOperations cartHashOperations = getCartHashOperations(memberId);
        List<CartItemDTO> cartItems = cartHashOperations.values();
        return cartItems;
    }

    /**
     * 删除用户购物车(清空购物车)
     */
    @Override
    public boolean deleteCart() {
        String key = OmsConstants.CART_PREFIX + MemberUtils.getMemberId();
        redisTemplate.delete(key);
        return true;
    }

    /**
     * 添加商品至购物车
     */
    @Override
    public boolean addCartItem(Long skuId) {
        Long memberId;
        try {
            memberId = MemberUtils.getMemberId();
        } catch (Exception e) {
            throw new BizException(ResultCode.TOKEN_INVALID_OR_EXPIRED);
        }
        BoundHashOperations cartHashOperations = getCartHashOperations(memberId);
        String hKey = skuId + "";

        CartItemDTO cartItem;
        // 购物车已存在该商品，更新商品数量
        if (cartHashOperations.get(hKey) != null) {
            cartItem = (CartItemDTO) cartHashOperations.get(hKey);
            cartItem.setCount(cartItem.getCount() + 1); // 点击一次“加入购物车”，数量+1
            cartItem.setChecked(true);
            cartHashOperations.put(hKey, cartItem);
            return true;
        }
        // 购物车不存在该商品，添加商品至购物车
        cartItem = new CartItemDTO();
        CompletableFuture<Void> cartItemCompletableFuture = CompletableFuture.runAsync(() -> {
            SkuInfoDTO skuInfo = skuFeignService.getSkuInfo(skuId).getData();
            if (skuInfo != null) {
                BeanUtil.copyProperties(skuInfo,cartItem);
                cartItem.setCount(1);
                cartItem.setChecked(true);
            }
        });
        CompletableFuture.allOf(cartItemCompletableFuture).join();

        Assert.isTrue(cartItem.getSkuId() != null,"商品不存在");
        cartHashOperations.put(hKey, cartItem);
        return true;
    }

    /**
     * 更新购物车总商品数量、选中状态
     */
    @Override
    public boolean updateCartItem(CartItemDTO cartItem) {
        Long memberId;
        try {
            memberId = MemberUtils.getMemberId();
        } catch (Exception e) {
            throw new BizException(ResultCode.TOKEN_INVALID_OR_EXPIRED);
        }
        BoundHashOperations cartHashOperations = getCartHashOperations(memberId);
        String hKey = cartItem.getSkuId() + "";
        if (cartHashOperations.get(hKey) != null) {
            CartItemDTO cacheCartItem = (CartItemDTO) cartHashOperations.get(hKey);
            if (cartItem.getChecked() != null) {
                cacheCartItem.setChecked(cartItem.getChecked());
            }
            if (cartItem.getCount() != null) {
                cacheCartItem.setCount(cartItem.getCount());
            }
            cartHashOperations.put(hKey, cacheCartItem);
        }
        return true;
    }

    /**
     * 移除购物车的商品
     */
    @Override
    public boolean removeCartItem(Long skuId) {
        Long memberId;
        try {
            memberId = MemberUtils.getMemberId();
        } catch (Exception e) {
            throw new BizException(ResultCode.TOKEN_INVALID_OR_EXPIRED);
        }
        BoundHashOperations cartHashOperations = getCartHashOperations(memberId);
        String hKey = skuId + "";
        cartHashOperations.delete(hKey);
        return true;
    }


    /**
     * 设置商品全选
     */
    @Override
    public boolean checkAll(boolean checked) {
        Long memberId;
        try {
            memberId = MemberUtils.getMemberId();
        } catch (Exception e) {
            throw new BizException(ResultCode.TOKEN_INVALID_OR_EXPIRED);
        }
        BoundHashOperations cartHashOperations = getCartHashOperations(memberId);
        for (Object value : cartHashOperations.values()) {
            CartItemDTO cartItem = (CartItemDTO) value;
            cartItem.setChecked(checked);
            String hKey = cartItem.getSkuId() + "";
            cartHashOperations.put(hKey, cartItem);
        }
        return true;
    }


    /**
     * 移除购物车选中的商品
     * — 场景：支付后删除购物车的商品
     */
    @Override
    public boolean removeCheckedItem() {
        Long memberId;
        try {
            memberId = MemberUtils.getMemberId();
        } catch (Exception e) {
            throw new BizException(ResultCode.TOKEN_INVALID_OR_EXPIRED);
        }
        BoundHashOperations cartHashOperations = getCartHashOperations(memberId);
        for (Object value : cartHashOperations.values()) {
            CartItemDTO cartItem = (CartItemDTO) value;
            if (cartItem.getChecked()) {
                cartHashOperations.delete(cartItem.getSkuId() + "");
            }
        }
        return true;
    }

    /**
     * 获取第一层，即某个用户的购物车
     */
    private BoundHashOperations getCartHashOperations(Long memberId) {
        String cartKey = OmsConstants.CART_PREFIX + memberId;
        BoundHashOperations operations = redisTemplate.boundHashOps(cartKey);
        return operations;
    }
}
