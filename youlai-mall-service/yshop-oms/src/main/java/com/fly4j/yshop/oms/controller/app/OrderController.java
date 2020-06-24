package com.fly4j.yshop.oms.controller.app;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.yshop.oms.pojo.dto.OrderDTO;
import com.fly4j.yshop.oms.service.IOmsOrderService;
import com.fly4j.yshop.pms.feign.admin.PmsFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags="APP-订单接口")
@RestController
@RequestMapping("/api.app/v1/orders")
public class OrderController {

    @Resource
    private PmsFeign pmsFeign;

    @Resource
    private IOmsOrderService iOmsOrderService;

    @ApiOperation(value = "订单提交", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderDTO", value = "订单JSON", required = true, paramType = "body", dataType = "OrderDTO"),
    })
    @PostMapping
    public R submit(@RequestBody OrderDTO orderDTO) {
        return iOmsOrderService.submit(orderDTO);
    }

    @ApiOperation(value = "生成token",httpMethod = "POST")
    @PostMapping("token")
    public String token(){
        return iOmsOrderService.token();
    }
}
