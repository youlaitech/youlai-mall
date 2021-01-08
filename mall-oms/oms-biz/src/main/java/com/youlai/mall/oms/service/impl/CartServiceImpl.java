package com.youlai.mall.oms.service.impl;

import com.youlai.common.core.result.Result;
import com.youlai.common.web.util.WebUtils;
import com.youlai.mall.pms.api.ProductFeignService;
import com.youlai.mall.oms.bo.CartItemBo;
import com.youlai.mall.oms.bo.CartItemCheckBo;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.oms.pojo.vo.CartItemVo;
import com.youlai.mall.oms.pojo.vo.CartVo;
import com.youlai.mall.oms.service.CartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.youlai.common.core.constant.RedisKeyConstants.MALL_CART_KEY;

/**
 * 购物车模块搭建步骤
 * 1、用户登录后才能操作购物车
 * *********添加购物车*************
 * 1、获取当前登录用户
 * 2、添加商品到购物车
 * 2.1、如果购物车中已经有该商品的相关数据
 * 2.1.1、修改购物车商品数量
 * 2.2、如果购物车中没有该商品，新增该商品
 * 2.2.1、调用商品服务查询商品sku详情
 * 2.2.2、封装CartItem实体类
 * *********修改购物车*************
 * *********删除购物车*************
 * *********查询购物车*************
 */

@Service
@Slf4j
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private RedisTemplate redisTemplate;

    private ProductFeignService productFeignService;


    @Override
    public void save(String skuId) throws ExecutionException, InterruptedException {
        log.info("添加商品到购物车，form:{}", skuId);
        BoundHashOperations cartOps = getCartOps();
        if (cartOps.get(skuId) != null) {
            CartItemVo cartItem = (CartItemVo) cartOps.get(skuId);
            Integer number = cartItem.getNumber() +1;
            cartItem.setNumber(number);
            cartOps.put(skuId, cartItem);
            return;
        }

        CartItemVo cartItem = new CartItemVo();
        // 添加新商品到购物车
        CompletableFuture<Void> skuInfoFuture = CompletableFuture.runAsync(() -> {
            //1、远程查询商品详情
            Result<SkuDTO> skuInfo = productFeignService.getSkuById(Long.parseLong(skuId));
            SkuDTO data = skuInfo.getData();
            cartItem.setSkuId(Long.parseLong(skuId));
            cartItem.setChecked(true);
            cartItem.setSkuName(data.getName());
            cartItem.setSkuImg(data.getPicUrl());
            cartItem.setNumber(1);
            cartItem.setPrice(data.getPrice());
            cartItem.setStock(data.getStock());

        });
        //2、远程查询商品属性
        //3、远程查询库存
        CompletableFuture<Void> allOf = CompletableFuture.allOf(skuInfoFuture);
        allOf.get();
        cartOps.put(skuId, cartItem);


    }

    @Override
    public void update(CartItemBo cartItemBo) {
        log.info("修改购物车商品数量，form:{}", cartItemBo);
        BoundHashOperations cartOps = getCartOps();
        CartItemVo cartItem = (CartItemVo) cartOps.get(cartItemBo.getSkuId().toString());
        if (cartItem == null) {
            return;
        }
        cartItem.setNumber(cartItemBo.getNumber());
        cartOps.put(cartItemBo.getSkuId().toString(), cartItem);
    }

    @Override
    public void check(CartItemCheckBo cartItemCheckBo) {
        log.info("修改购物车商品选中状态，form:{}", cartItemCheckBo);
        BoundHashOperations cartOps = getCartOps();
        CartItemVo cartItem = (CartItemVo) cartOps.get(cartItemCheckBo.getSkuId().toString());
        if (cartItem == null) {
            return;
        }
        cartItem.setChecked(cartItemCheckBo.getCheck() == 1);
        cartOps.put(cartItemCheckBo.getSkuId().toString(), cartItem);
    }

    @Override
    public void checkAll(Integer check) {
        log.info("全选/全不选购物车商品状态，check:{}", check);
        BoundHashOperations cartOps = getCartOps();
        for (Object value : cartOps.values()) {
            CartItemVo cartItem = (CartItemVo) value;
            cartItem.setChecked(check == 1);
            cartOps.put(cartItem.getSkuId().toString(), cartItem);
        }
    }

    @Override
    public void delete(Long skuId) {
        log.info("删除购物车，商品id:{}", skuId);
        BoundHashOperations cartOps = getCartOps();
        cartOps.delete(skuId);
    }

    @Override
    public void deleteBatch(List<String> skuIds) {
        log.info("批量删除购物车，商品id集合：{}", skuIds);
        BoundHashOperations cartOps = getCartOps();
        for (String skuId : skuIds) {
            cartOps.delete(skuId);
        }
    }

    @Override
    public CartVo detail() {
        log.info("查询购物车详情");
        BoundHashOperations cartOps = getCartOps();
        List<CartItemVo> items = cartOps.values();
        CartVo cartVo = new CartVo();
        cartVo.setItems(items);
        return cartVo;
    }

    @Override
    public void clear() {
        log.info("清空购物车");
        Long userId = WebUtils.getUserId();
        String cartKey = MALL_CART_KEY + userId;
        redisTemplate.delete(cartKey);

    }

    private BoundHashOperations getCartOps() {
        Long userId = WebUtils.getUserId();
//        Long userId = 1343913824748593153L;
        String cartKey = MALL_CART_KEY + userId;
        BoundHashOperations operations = redisTemplate.boundHashOps(cartKey);
        return operations;
    }
}
