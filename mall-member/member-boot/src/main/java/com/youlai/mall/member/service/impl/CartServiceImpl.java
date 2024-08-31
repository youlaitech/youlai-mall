package com.youlai.mall.member.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.youlai.common.constant.RedisConstants;
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.mall.member.dto.CartItemDTO;
import com.youlai.mall.product.api.SkuFeignClient;
import com.youlai.mall.product.model.dto.SkuDTO;
import com.youlai.mall.member.convert.CartConverter;
import com.youlai.mall.member.model.cache.CartItemCache;
import com.youlai.mall.member.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 购物车模块
 * <p>
 * 核心技术：BoundHashOperations
 * 数据格式：
 * -- key <--> 商品列表
 * ---- hKey:value <--> skuId 商品1
 * ---- hKey:value <--> 商品2
 * ---- hKey:value <--> 商品3
 *
 * @author Ray
 * @since 4.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final RedisTemplate redisTemplate;
    private final SkuFeignClient skuFeignClient;
    private final CartConverter cartConverter;

    /**
     * 获取用户购物车列表
     */
    @Override
    public List<CartItemDTO> listCartItems(Long memberId) {
        if (memberId == null) {
            return Collections.emptyList();
        }
        BoundHashOperations<String, String, CartItemCache> cartOperations = getCartHashOps(memberId);
        List<CartItemCache> cartItemCaches = cartOperations.values();
        if (CollectionUtil.isEmpty(cartItemCaches)) {
            return Collections.emptyList();
        }

        List<CartItemDTO> cartItems = cartConverter.convertToDto(cartItemCaches);

        // 动态获取商品信息(实时商品价格、库存、图片)到购物车列表
        List<Long> skuIds = cartItems.stream().map(CartItemDTO::getSkuId).toList();
        List<SkuDTO> skuList = skuFeignClient.listSkusByIds(skuIds);

        // 创建SKU ID到SkuInfoDto的映射，以优化查找性能
        Map<Long, SkuDTO> skuIdToSkuInfoMap = skuList.stream()
                .collect(Collectors.toMap(SkuDTO::getId, Function.identity()));

        // 为每个购物车项更新实时信息
        cartItems.forEach(cartItem -> {
            SkuDTO skuDTO = skuIdToSkuInfoMap.get(cartItem.getSkuId());
            if (skuDTO != null) {
                cartItem.setStock(skuDTO.getStock());
                cartItem.setPrice(skuDTO.getPrice());
                cartItem.setImgUrl(skuDTO.getImgUrl());
                cartItem.setSpuName(skuDTO.getSpuName());
            }
        });
        return cartItems;
    }

    /**
     * 清空用户购物车
     */
    @Override
    public void clearCart() {
        redisTemplate.delete(buildCartKey(SecurityUtils.getMemberId()));
    }

    /**
     * 添加商品至购物车
     */
    @Override
    public void addCartItem(Long skuId, Integer quantity) {
        Long memberId = SecurityUtils.getMemberId();
        BoundHashOperations<String, String, CartItemCache> cartHashOps = redisTemplate.boundHashOps(buildCartKey(memberId));

        String hKey = skuId + "";

        CartItemCache cartItemCache = cartHashOps.get(hKey);
        // 购物车已存在该商品，更新商品数量
        if (cartItemCache != null) {
            cartItemCache.setQuantity(cartItemCache.getQuantity() + quantity);
            cartItemCache.setChecked(true);
        } else {
            // 购物车中不存在该商品，新增商品到购物车
            cartItemCache = new CartItemCache();
            cartItemCache.setSkuId(skuId);
            cartItemCache.setQuantity(quantity);
            cartItemCache.setChecked(true);
        }
        cartHashOps.put(hKey, cartItemCache);
    }

    /**
     * 移除购物车指定的商品
     */
    @Override
    public void removeCartItem(Long skuId) {
        Long memberId = SecurityUtils.getMemberId();
        BoundHashOperations<String, String, CartItemCache> cartHashOps = getCartHashOps(memberId);
        cartHashOps.delete(skuId);
    }

    /**
     * 删除选中的购物车商品
     * <p>
     * 订单支付成功，删除购物车中已选中的商品
     */
    @Override
    public void removeCheckedCartItems(Long memberId) {
        BoundHashOperations<String, String, CartItemCache> cartHashOps = getCartHashOps(memberId);

        List<CartItemCache> cartItemCaches = cartHashOps.values();
        if (CollectionUtil.isNotEmpty(cartItemCaches)) {
            cartItemCaches.forEach(cartItemCache -> {
                if (cartItemCache.getChecked()) {
                    cartHashOps.delete(cartItemCache.getSkuId() + "");
                }
            });
        }
    }


    /**
     * 切换购物车商品选中状态(全选/全不选)
     */
    @Override
    public void toggleCheckAllItems(boolean checked) {
        Long memberId = SecurityUtils.getMemberId();
        BoundHashOperations<String, String, CartItemCache> cartHashOps = getCartHashOps(memberId);

        List<CartItemCache> cartItemCaches = cartHashOps.values();
        if (CollectionUtil.isNotEmpty(cartItemCaches)) {
            cartItemCaches.forEach(cartItemCache -> {
                cartItemCache.setChecked(checked);
                cartHashOps.put(cartItemCache.getSkuId() + "", cartItemCache);
            });
        }
    }

    /**
     * 获取购物车的Hash操作对象
     *
     * @param memberId 会员ID
     */
    private BoundHashOperations<String, String, CartItemCache> getCartHashOps(Long memberId) {
        return redisTemplate.boundHashOps(buildCartKey(memberId));
    }

    /**
     * 获取购物车的key
     *
     * @param memberId 会员ID
     */
    private String buildCartKey(Long memberId) {
        return RedisConstants.MEMBER_KEY_PREFIX + memberId + RedisConstants.MEMBER_CART_KEY_SUFFIX;
    }
}
