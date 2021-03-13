package com.youlai.mall.oms.service.impl;

import com.youlai.common.result.Result;
import com.youlai.common.web.util.RequestUtils;
import com.youlai.mall.pms.api.app.InventoryFeignService;
import com.youlai.mall.oms.bo.CartItemBO;
import com.youlai.mall.oms.bo.CartItemCheckBo;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.oms.pojo.vo.CartItemVO;
import com.youlai.mall.oms.pojo.vo.CartVO;
import com.youlai.mall.oms.service.CartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.youlai.mall.oms.common.RedisConstants.MALL_CART_KEY;


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

    private InventoryFeignService inventoryFeignService;


    @Override
    public void save(String skuId) throws ExecutionException, InterruptedException {
        log.info("添加商品到购物车，form:{}", skuId);
        BoundHashOperations cartOps = getCartOps();
        if (cartOps.get(skuId) != null) {
            CartItemVO cartItem = (CartItemVO) cartOps.get(skuId);
            Integer number = cartItem.getNumber() + 1;
            cartItem.setNumber(number);
            cartOps.put(skuId, cartItem);
            return;
        }

        CartItemVO cartItem = new CartItemVO();
        // 添加新商品到购物车
        CompletableFuture<Void> skuInfoFuture = CompletableFuture.runAsync(() -> {
            //1、远程查询商品详情
            Result<SkuDTO> skuInfo = inventoryFeignService.getInventoryById(Long.parseLong(skuId));
            SkuDTO data = skuInfo.getData();
            cartItem.setSkuId(Long.parseLong(skuId));
            cartItem.setChecked(true);
            cartItem.setSkuName(data.getName());
            cartItem.setSkuImg(data.getPic());
            cartItem.setNumber(1);
            cartItem.setPrice(data.getPrice());
            cartItem.setInventory(data.getInventory());

        });
        //2、远程查询商品属性
        //3、远程查询库存
        CompletableFuture<Void> allOf = CompletableFuture.allOf(skuInfoFuture);
        allOf.get();
        cartOps.put(skuId, cartItem);


    }

    @Override
    public void update(CartItemBO cartItemBo) {
        log.info("修改购物车商品数量，form:{}", cartItemBo);
        BoundHashOperations cartOps = getCartOps();
        CartItemVO cartItem = (CartItemVO) cartOps.get(cartItemBo.getSkuId().toString());
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
        CartItemVO cartItem = (CartItemVO) cartOps.get(cartItemCheckBo.getSkuId().toString());
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
            CartItemVO cartItem = (CartItemVO) value;
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
    public CartVO detail() {
        log.info("查询购物车详情");
        BoundHashOperations cartOps = getCartOps();
        List<CartItemVO> items = cartOps.values();
        CartVO cartVo = new CartVO();
        cartVo.setItems(items);
        return cartVo;
    }

    @Override
    public void clear() {
        log.info("清空购物车");
        Long userId = RequestUtils.getUserId();
        String cartKey = MALL_CART_KEY + userId;
        redisTemplate.delete(cartKey);

    }

    @Override
    public void cleanSelected() {
        log.info("清空购物车中已选择商品");
        BoundHashOperations cartOps = getCartOps();
        for (Object value : cartOps.values()) {
            CartItemVO cartItem = (CartItemVO) value;
            if (cartItem.isChecked()) {
                log.info("清空购物车中商品，商品id：{} | 名称：{}", cartItem.getSkuId(), cartItem.getSkuName());
                cartOps.delete(cartItem.getSkuId().toString());
            }
        }
    }

    private BoundHashOperations getCartOps() {
        Long userId = RequestUtils.getUserId();
        String cartKey = MALL_CART_KEY + userId;
        BoundHashOperations operations = redisTemplate.boundHashOps(cartKey);
        return operations;
    }
}
