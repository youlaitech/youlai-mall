package com.youlai.mall.oms.service.impl;

import com.youlai.common.result.Result;
import com.youlai.common.web.util.RequestUtils;
import com.youlai.mall.pms.api.app.InventoryFeignService;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.oms.pojo.vo.CartItemVO;
import com.youlai.mall.oms.pojo.vo.CartVO;
import com.youlai.mall.oms.service.ICartService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.youlai.mall.oms.constant.OmsConstants.CART_KEY;


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
public class CartServiceImpl implements ICartService {

    private RedisTemplate redisTemplate;

    private InventoryFeignService inventoryFeignService;

    @Override
    @SneakyThrows
    public void addCartItem(Long skuId) {
        BoundHashOperations cartHashOpts = getCartHashOpts();
        String hKey = skuId.toString(); // redis 的 hash key
        if (cartHashOpts.get(hKey) != null) {
            CartItemVO cartItem = (CartItemVO) cartHashOpts.get(hKey);
            cartItem.setNum(cartItem.getNum() + 1);
            cartItem.setSubtotal(cartItem.getPrice() * cartItem.getNum());
            cartHashOpts.put(hKey, cartItem);
            return;
        }
        CartItemVO cartItem = new CartItemVO();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            // 远程查询商品详情
            Result<SkuDTO> result = inventoryFeignService.getSkuById(skuId);
            SkuDTO sku = result.getData();

            cartItem.setSkuId(skuId)
                    .setChecked(true)
                    .setSkuName(sku.getName())
                    .setSkuPic(sku.getPic())
                    .setNum(1)
                    .setPrice(sku.getPrice())
                    .setInventory(sku.getInventory())
                    .setSubtotal(1 * sku.getPrice());
        });
        CompletableFuture<Void> allOf = CompletableFuture.allOf(future);
        allOf.get();
        cartHashOpts.put(hKey, cartItem);
    }

    @Override
    public void updateCartItem(Long skuId, Integer num, Boolean checked) {
        BoundHashOperations cartHashOpts = getCartHashOpts();
        String hKey = skuId + "";
        CartItemVO cartItem = (CartItemVO) cartHashOpts.get(hKey);
        if (cartItem != null) {
            if (num != null) {
                cartItem.setNum(num);
                cartItem.setSubtotal(num * cartItem.getPrice());
            }
            if (checked != null) {
                cartItem.setChecked(checked);
            }
            cartHashOpts.put(hKey, cartItem);
        }
    }

    @Override
    public void checkAll(Boolean checked) {
        BoundHashOperations cartHashOpts = getCartHashOpts();
        for (Object value : cartHashOpts.values()) {
            CartItemVO cartItem = (CartItemVO) value;
            cartItem.setChecked(checked);
            String hKey = cartItem.getSkuId() + "";
            cartHashOpts.put(hKey, cartItem);
        }
    }

    @Override
    public void deleteCartItem(Long skuId) {
        BoundHashOperations cartHashOpts = getCartHashOpts();
        String hKey = skuId.toString();
        cartHashOpts.delete(hKey);
    }

    @Override
    public CartVO getCart() {
        BoundHashOperations cartHashOpts = getCartHashOpts();
        List<CartItemVO> items = cartHashOpts.values();
        CartVO cartVo = new CartVO();
        cartVo.setItems(items);
        return cartVo;
    }

    @Override
    public void deleteCart() {
        Long userId = RequestUtils.getUserId();
        String key = CART_KEY + userId;
        redisTemplate.delete(key);
    }

    @Override
    public void deleteSelectedItem() {
        log.info("清空购物车中已选择商品");
        BoundHashOperations cartHashOpts = getCartHashOpts();
        for (Object value : cartHashOpts.values()) {
            CartItemVO cartItem = (CartItemVO) value;
            if (cartItem.isChecked()) {
                log.info("清空购物车中商品，商品id：{} | 名称：{}", cartItem.getSkuId(), cartItem.getSkuName());
                cartHashOpts.delete(cartItem.getSkuId().toString());
            }
        }
    }

    private BoundHashOperations getCartHashOpts() {
        Long userId = RequestUtils.getUserId();
        String cartKey = CART_KEY + userId;
        BoundHashOperations operations = redisTemplate.boundHashOps(cartKey);
        return operations;
    }
}
