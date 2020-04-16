package com.fly4j.yshop.oms.controller.app;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.yshop.oms.pojo.dto.OrderDTO;
import com.fly4j.yshop.pms.feign.PmsFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags="APP订单接口")
@RestController
@RequestMapping("/api/app/v1/orders")
public class OrderController {

    @Autowired
    private PmsFeign pmsFeign;

    @ApiOperation(value = "订单商品详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long"),
    })
    @GetMapping("/spu/{id}")
    public R getSpu(@PathVariable Long id) {
        return pmsFeign.getSpuById(id);
    }

    @ApiOperation(value = "订单提交", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderDTO", value = "订单JSON", required = true, paramType = "body", dataType = "OrderDTO"),
    })
    @PostMapping
    public R submit(@RequestBody OrderDTO orderDTO) {
        return null;
    }

}
