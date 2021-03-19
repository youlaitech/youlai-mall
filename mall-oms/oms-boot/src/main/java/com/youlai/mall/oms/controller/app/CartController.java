package com.youlai.mall.oms.controller.app;

import com.youlai.common.result.Result;
import com.youlai.mall.oms.pojo.vo.CartVO;
import com.youlai.mall.oms.service.ICartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */

@Api(tags = "【移动端】购物车")
@RestController
@RequestMapping("/api.app/v1/carts")
@Slf4j
@AllArgsConstructor
public class CartController {

    private ICartService cartService;

    @ApiOperation(value = "查询购物车")
    @GetMapping
    public Result getCart() {
        CartVO cart = cartService.getCart();
        return Result.success(cart);
    }

    @ApiOperation(value = "全选/全不选 购物车商品")
    @ApiImplicitParam(name = "checked", value = "全选/全不选", required = true, paramType = "param", dataType = "Boolean")
    @PatchMapping("/_check")
    public Result check(boolean checked) {
        boolean result = cartService.checkAll(checked);
        return Result.judge(result);
    }

    @ApiOperation(value = "清空购物车")
    @DeleteMapping
    public Result deleteCart() {
        boolean result = cartService.deleteCart();
        return Result.judge(result);
    }


    @ApiOperation(value = "添加购物车商品")
    @ApiImplicitParam(name = "skuId", value = "SKU ID", required = true, paramType = "param", dataType = "Long")
    @PostMapping
    public Result addCartItem(@RequestParam Long skuId) {
        cartService.addCartItem(skuId);
        return Result.success();
    }

    @ApiOperation(value = "更新购物车商品")
    @PutMapping("/skuId/{skuId}")
    public Result updateCartItem(@PathVariable Long skuId,@RequestBody CartVO.CartItem cartItem) {
        cartItem.setSkuId(skuId);
        boolean result = cartService.updateCartItem(cartItem);
        return Result.judge(result);
    }


    @ApiOperation(value = "删除购物车商品")
    @ApiImplicitParam(name = "skuId", value = "SKU ID", required = true, paramType = "param", dataType = "Long")
    @DeleteMapping("/skuId/{skuId}")
    public Result removeCartItem(@PathVariable Long skuId) {
        boolean result = cartService.removeCartItem(skuId);
        return Result.judge(result);
    }


}
