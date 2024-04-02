package com.youlai.mall.ums.service.impl;

import com.youlai.common.constant.RedisConstants;
import com.youlai.common.result.ResultCode;
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.pms.api.SkuFeignClient;
import com.youlai.mall.pms.model.dto.SkuInfoDTO;
import com.youlai.mall.ums.convert.CartConverter;
import com.youlai.mall.ums.model.vo.CartItemVo;
import com.youlai.mall.ums.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


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
    private final SkuFeignClient skuFeignClient;
    private final CartConverter cartConverter;

    @Override
    public List<CartItemVo> listCartItems(Long memberId) {
        if (memberId != null) {
            BoundHashOperations cartHashOperations = getCartHashOperations(memberId);
            List<CartItemVo> cartItems = cartHashOperations.values();
            return cartItems;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 删除用户购物车(清空购物车)
     */
    @Override
    public boolean deleteCart() {
        String key = RedisConstants.MEMBER_KEY_PREFIX + SecurityUtils.getMemberId()+ RedisConstants.MEMBER_CART_KEY_SUFFIX ;
        redisTemplate.delete(key);
        return true;
    }

    /**
     * 添加商品至购物车
     */
    @Override
    public boolean addCartItem(Long skuId) {
        Long memberId = SecurityUtils.getMemberId();
        BoundHashOperations<String, String, CartItemVo> cartHashOperations = getCartHashOperations(memberId);
        String hKey = String.valueOf(skuId);

        CartItemVo cartItem = cartHashOperations.get(hKey);

        if (cartItem != null) {
            // 购物车已存在该商品，更新商品数量
            cartItem.setCount(cartItem.getCount() + 1); // 点击一次“加入购物车”，数量+1
            cartItem.setChecked(true);
        } else {
            // 购物车中不存在该商品，新增商品到购物车
            SkuInfoDTO skuInfo = skuFeignClient.getSkuInfo(skuId);
            if (skuInfo != null) {
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

    /**
     * 移除购物车的商品
     */
    @Override
    public boolean removeCartItem(Long skuId) {
        Long memberId;
        try {
            memberId = SecurityUtils.getMemberId();
        } catch (Exception e) {
            throw new BizException(ResultCode.TOKEN_INVALID);
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
            throw new BizException(ResultCode.TOKEN_INVALID);
        }
        BoundHashOperations cartHashOperations = getCartHashOperations(memberId);
        for (Object value : cartHashOperations.values()) {
            CartItemVo cartItem = (CartItemVo) value;
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
            throw new BizException(ResultCode.TOKEN_INVALID);
        }
        BoundHashOperations cartHashOperations = getCartHashOperations(memberId);
        for (Object value : cartHashOperations.values()) {
            CartItemVo cartItem = (CartItemVo) value;
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
        String cartKey = getCartKey(memberId);
        BoundHashOperations operations = redisTemplate.boundHashOps(cartKey);
        return operations;
    }

    private String getCartKey(Long memberId) {
       return RedisConstants.MEMBER_KEY_PREFIX + memberId+ RedisConstants.MEMBER_CART_KEY_SUFFIX;
    }
}
