package com.youlai.mall.oms.service.app.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.youlai.common.result.ResultCode;
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.oms.constant.OrderConstants;
import com.youlai.mall.oms.converter.CartConverter;
import com.youlai.mall.oms.model.dto.CartItemDTO;
import com.youlai.mall.oms.service.app.CartService;
import com.youlai.mall.pms.api.SkuFeignClient;
import com.youlai.mall.pms.pojo.dto.SkuInfoDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * 购物车模块
 * <p>
 * 核心技术：BoundHashOperations
 * 数据格式：
 * -- key <--> 商品列表
 * ---- hKey:value <--> skuId 商品1
 * ---- hKey:value <--> 商品2
 * ---- hKey:value <--> 商品3
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final RedisTemplate redisTemplate;
    private final SkuFeignClient skuFeignService;
    private final CartConverter cartConverter;

    @Override
    public List<CartItemDTO> listCartItems(Long memberId) {
        if (memberId != null) {
            BoundHashOperations cartHashOperations = getCartHashOperations(memberId);
            List<CartItemDTO> cartItems = cartHashOperations.values();
            return cartItems;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 删除用户购物车(清空购物车)
     */
    @Override
    public boolean deleteCart() {
        String key = OrderConstants.MEMBER_CART_PREFIX + SecurityUtils.getMemberId();
        redisTemplate.delete(key);
        return true;
    }

    /**
     * 添加商品至购物车
     */
    @Override
    public boolean addCartItem(Long skuId) {
        Long memberId = SecurityUtils.getMemberId();
        BoundHashOperations<String, String, CartItemDTO> cartHashOperations = getCartHashOperations(memberId);
        String hKey = String.valueOf(skuId);

        CartItemDTO cartItem = cartHashOperations.get(hKey);

        if (cartItem != null) {
            // 购物车已存在该商品，更新商品数量
            cartItem.setCount(cartItem.getCount() + 1); // 点击一次“加入购物车”，数量+1
            cartItem.setChecked(true);
        } else {
            // 购物车中不存在该商品，新增商品到购物车
            SkuInfoDTO skuInfo = skuFeignService.getSkuInfo(skuId);
            if (skuInfo != null) {
                cartItem = cartConverter.sku2CartItem(skuInfo);
                cartItem.setCount(1);
                cartItem.setChecked(true);
            }
        }
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
            memberId = SecurityUtils.getMemberId();
        } catch (Exception e) {
            throw new BizException(ResultCode.INVALID_TOKEN);
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
            memberId = SecurityUtils.getMemberId();
        } catch (Exception e) {
            throw new BizException(ResultCode.INVALID_TOKEN);
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
            memberId = SecurityUtils.getMemberId();
        } catch (Exception e) {
            throw new BizException(ResultCode.INVALID_TOKEN);
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
     * 移除购物车选中的商品，作用场景：
     * <p>
     * 1.支付后删除购物车的商品
     */
    @Override
    public boolean removeCheckedItem() {
        Long memberId = SecurityUtils.getMemberId();
        if (memberId == null) {
            throw new BizException(ResultCode.INVALID_TOKEN);
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
        String cartKey = OrderConstants.MEMBER_CART_PREFIX + memberId;
        BoundHashOperations operations = redisTemplate.boundHashOps(cartKey);
        return operations;
    }
}
