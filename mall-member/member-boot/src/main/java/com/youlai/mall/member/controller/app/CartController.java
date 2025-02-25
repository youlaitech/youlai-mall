package com.youlai.mall.member.controller.app;

import com.youlai.common.result.Result;
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.mall.member.dto.CartItemDTO;
import com.youlai.mall.member.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 购物车控制层
 *
 * @author Ray.Hao
 * @since 2024/4/4
 */

@Tag(name = "App-购物车接口")
@RestController
@RequestMapping("/app-api/v1/cart-items")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @Operation(summary = "获取用户的购物车列表")
    @GetMapping
    public Result<List<CartItemDTO>> listCartItems() {
        Long memberId = SecurityUtils.getMemberId();
        List<CartItemDTO> list = cartService.listCartItems(memberId);
        return Result.success(list);
    }

    @Operation(summary = "添加商品至购物车")
    @PostMapping("/{skuId}")
    public Result addCartItem(
            @Parameter(description = "商品的唯一标识符") @PathVariable Long skuId,
            @Parameter(description = "商品数量变化值（正数表示增加对应数量，负数表示减少对应数量）") @RequestParam Integer quantity
    ) {
        cartService.addCartItem(skuId, quantity);
        return Result.success();
    }

    @Operation(summary = "清空当前用户的购物车")
    @DeleteMapping
    public Result clearCart() {
        cartService.clearCart();
        return Result.success();
    }

    @Operation(summary = "从购物车中移除指定商品")
    @DeleteMapping("/{skuId}")
    public Result removeCartItem(
            @Parameter(description = "商品SKU的ID")  @PathVariable Long skuId
    ) {
        cartService.removeCartItem(skuId);
        return Result.success();
    }


    @Operation(summary = "切换购物车商品的选中状态")
    @PatchMapping("/toggle-check")
    public Result toggleCheckAllItems(
            @Parameter(description = "是否全选(true:全选;false:全不选)") boolean checked
    ) {
         cartService.toggleCheckAllItems(checked);
        return Result.success();
    }
}
