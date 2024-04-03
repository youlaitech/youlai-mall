package com.youlai.mall.ums.controller.app;

import com.youlai.common.result.Result;
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.mall.ums.model.vo.CartItemVo;
import com.youlai.mall.ums.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * App-购物车接口
 *
 * @author huawei
 * @since 0.0.1
 */

@Tag(name = "App-购物车接口")
@RestController
@RequestMapping("/app-api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @Operation(summary = "获取用户的购物车列表")
    @GetMapping
    public Result listCartItems() {
        Long memberId = SecurityUtils.getMemberId();
        List<CartItemVo> result = cartService.listCartItems(memberId);
        return Result.success(result);
    }

    @Operation(summary = "添加指定数量的商品到购物车")
    @PostMapping("/sku/{skuId}")
    public Result addCartItem(
            @Parameter(name = "SKU ID") @PathVariable Long skuId,
            @Parameter(name = "添加购物车的商品数量(如果减少)") @RequestParam Integer quantity
    ) {
        boolean result = cartService.addCartItem(skuId, quantity);
        return Result.success();
    }

    @Operation(summary = "清空当前用户的购物车")
    @DeleteMapping
    public Result deleteCart() {
        boolean result = cartService.deleteCart();
        return Result.judge(result);
    }

    @Operation(summary = "从购物车中移除指定商品")
    @DeleteMapping("/skuId/{skuId}")
    public Result removeCartItem(@PathVariable Long skuId) {
        boolean result = cartService.removeCartItem(skuId);
        return Result.judge(result);
    }


    @Operation(summary = "切换购物车商品的选中状态")
    @PatchMapping("/check-all")
    public Result check(
            @Parameter(name = "是否全选(true:全选;false:全不选)") boolean checked
    ) {
        boolean result = cartService.checkAll(checked);
        return Result.judge(result);
    }
}
