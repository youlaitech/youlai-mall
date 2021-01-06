package com.youlai.mall.oms.controller.app;

import com.youlai.common.core.result.Result;
import com.youlai.mall.oms.bo.CartItemBo;
import com.youlai.mall.oms.bo.CartItemCheckBo;
import com.youlai.mall.oms.pojo.vo.CartVo;
import com.youlai.mall.oms.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Api(tags = "购物车接口")
@RestController
@RequestMapping("/api.app/v1/carts")
@Slf4j
@AllArgsConstructor
public class CartController {

    private CartService cartService;

    @ApiOperation(value = "查询购物车", httpMethod = "GET")
    @GetMapping
    public Result<CartVo> detail() {
        CartVo cart = cartService.detail();
        return Result.success(cart);
    }

    @ApiOperation(value = "添加购物车", httpMethod = "POST")
    @ApiImplicitParam(name = "skuId", value = "商品SKU Id", required = true, paramType = "param", dataType = "String")
    @PostMapping
    public Result<Object> save(@RequestParam("skuId") String skuId) throws ExecutionException, InterruptedException {
        cartService.save(skuId);
        return Result.success();
    }

    @ApiOperation(value = "修改购物车商品数量", httpMethod = "PUT")
    @ApiImplicitParam(name = "cartItemBo", value = "实体JSON对象", required = true, paramType = "body", dataType = "CartItemBo")
    @PutMapping
    public Result<Object> update(@Validated @RequestBody CartItemBo cartItemBo) {
        cartService.update(cartItemBo);
        return Result.success();
    }

    @ApiOperation(value = "是否选择购物车中商品", httpMethod = "PUT")
    @ApiImplicitParam(name = "cartItemChooseBo", value = "实体JSON对象", required = true, paramType = "body", dataType = "CartItemChooseBo")
    @PutMapping("/check")
    public Result<Object> check(@Validated @RequestBody CartItemCheckBo cartItemCheckBo) {
        cartService.check(cartItemCheckBo);
        return Result.success();
    }

    @ApiOperation(value = "全选/全不选择购物车", httpMethod = "PUT")
    @ApiImplicitParam(name = "check", value = "全选/全不选", required = true, paramType = "param", dataType = "Integer")
    @PutMapping("/checkAll")
    public Result<Object> checkAll(@RequestParam("check") Integer check) {
        cartService.checkAll(check);
        return Result.success();
    }

    @ApiOperation(value = "批量删除购物车", httpMethod = "DELETE")
    @ApiImplicitParam(name = "skuIds", value = "商品sku id集合", required = true, paramType = "param", dataType = "List")
    @DeleteMapping
    public Result<Boolean> delete(@RequestParam("skuIds") List<String> skuIds) {
        cartService.deleteBatch(skuIds);
        return Result.success();
    }

    @ApiOperation(value = "清空购物车", httpMethod = "GET")
    @GetMapping("/clear")
    public Result<Boolean> clear() {
        cartService.clear();
        return Result.success();
    }

}
