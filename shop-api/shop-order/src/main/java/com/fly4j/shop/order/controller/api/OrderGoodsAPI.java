package com.fly4j.shop.order.controller.api;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.shop.order.pojo.dto.GoodsDTO;
import com.fly4j.shop.order.remote.IRemoteGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Mr.
 * @create: 2020-03-28 20:49
 **/
@Api(tags="订单商品接口")
@RestController
@RequestMapping("/api/v1/OrderGoods")
public class OrderGoodsAPI {

    @Autowired
    private IRemoteGoodsService iRemoteGoodsService;

    @ApiOperation("获取订单的商品")
    @GetMapping("/getOrderGoods")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品id", required = true, paramType = "query")
    })
    public R getOrderGoods(
            Long id
    ){
        R<GoodsDTO> orderGoods = iRemoteGoodsService.getOrderGoods(id);
        return orderGoods;
    }
}
