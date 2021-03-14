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
 * 购物车
 *
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

    private ICartService ICartService;

    @ApiOperation(value = "查询购物车", httpMethod = "GET")
    @GetMapping
    public Result getCart() {
        CartVO cart = ICartService.getCart();
        return Result.success(cart);
    }

    @ApiOperation(value = "添加购物车商品", httpMethod = "POST")
    @ApiImplicitParam(name = "skuId", value = "SKU ID", required = true, paramType = "param", dataType = "Long")
    @PostMapping
    public Result addCartItem(@RequestParam Long skuId) {
        ICartService.addCartItem(skuId);
        return Result.success();
    }

    @ApiOperation(value = "局部更新购物车商品", httpMethod = "PUT")
    @PutMapping("/skuId/{skuId}")
    public Result updateCartItem(
            @PathVariable Long skuId,
            Integer num,
            Boolean checked
    ) {
        ICartService.updateCartItem(skuId, num, checked);
        return Result.success();
    }

    @ApiOperation(value = "全选/全不选择购物车商品", httpMethod = "PUT")
    @ApiImplicitParam(name = "checked", value = "全选/全不选", required = true, paramType = "param", dataType = "Boolean")
    @PatchMapping("/batch")
    public Result checkAll(Boolean checked) {
        ICartService.checkAll(checked);
        return Result.success();
    }

    @ApiOperation(value = "删除购物车商品", httpMethod = "DELETE")
    @ApiImplicitParam(name = "skuId", value = "SKU ID集合", required = true, paramType = "param", dataType = "Long")
    @DeleteMapping("/skuId/{skuId}")
    public Result deleteCartItem(@PathVariable Long skuId) {
        ICartService.deleteCartItem(skuId);
        return Result.success();
    }

    @ApiOperation(value = "清空购物车", httpMethod = "DELETE")
    @DeleteMapping
    public Result deleteCart() {
        ICartService.deleteCart();
        return Result.success();
    }
}
