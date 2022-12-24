package com.youlai.mall.oms.controller.app;

import com.youlai.common.result.Result;
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.mall.oms.pojo.dto.CartItemDTO;
import com.youlai.mall.oms.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 「移动端」购物车接口
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */

@Api(tags = "「移动端」购物车接口")
@RestController
@RequestMapping("/app-api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @ApiOperation(value = "查询购物车")
    @GetMapping
    public <T> Result<T> getCart() {
        List<CartItemDTO> result = cartService.listCartItems(SecurityUtils.getMemberId());
        return Result.success((T) result);
    }

    @ApiOperation(value = "删除购物车")
    @DeleteMapping
    public <T> Result<T> deleteCart() {
        boolean result = cartService.deleteCart();
        return Result.judge(result);
    }

    @ApiOperation(value = "添加购物车商品")
    @PostMapping
    public <T> Result<T> addCartItem(@RequestParam Long skuId) {
        cartService.addCartItem(skuId);
        return Result.success();
    }

    @ApiOperation(value = "更新购物车商品")
    @PutMapping("/skuId/{skuId}")
    public <T> Result<T> updateCartItem(
            @PathVariable Long skuId,
            @RequestBody CartItemDTO cartItem
    ) {
        cartItem.setSkuId(skuId);
        boolean result = cartService.updateCartItem(cartItem);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除购物车商品")
    @DeleteMapping("/skuId/{skuId}")
    public <T> Result<T> removeCartItem(@PathVariable Long skuId) {
        boolean result = cartService.removeCartItem(skuId);
        return Result.judge(result);
    }

    @ApiOperation(value = "全选/全不选购物车商品")
    @PatchMapping("/_check")
    public <T> Result<T> check(
         @ApiParam("全选/全不选") boolean checked
    ) {
        boolean result = cartService.checkAll(checked);
        return Result.judge(result);
    }
}
