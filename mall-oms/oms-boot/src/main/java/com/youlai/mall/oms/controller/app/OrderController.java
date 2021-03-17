package com.youlai.mall.oms.controller.app;

import com.youlai.common.result.Result;
import com.youlai.mall.oms.pojo.dto.OrderConfirmDTO;
import com.youlai.mall.oms.pojo.vo.OrderConfirmVO;
import com.youlai.mall.oms.pojo.vo.OrderListVO;
import com.youlai.mall.oms.pojo.vo.OrderSubmitVO;
import com.youlai.mall.oms.pojo.dto.OrderSubmitDTO;
import com.youlai.mall.oms.service.IOrderService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@Api(tags = "【移动端】订单服务")
@RestController
@RequestMapping("/api.app/v1/orders")
@Slf4j
@AllArgsConstructor
public class OrderController {

    private IOrderService orderService;

    @ApiOperation(value = "确认订单", httpMethod = "POST")
    @ApiImplicitParam(name = "orderConfirm",value = "确认订单信息",required = true, paramType = "body", dataType = "OrderConfirmDTO")
    @PostMapping("/_confirm")
    public Result<OrderConfirmVO> confirm(@RequestBody OrderConfirmDTO orderConfirm) {
        OrderConfirmVO result = orderService.confirm(orderConfirm);
        return Result.success(result);
    }

    @ApiOperation(value = "提交订单", httpMethod = "POST")
    @ApiImplicitParam(name = "orderSubmitDTO", value = "提交订单信息", required = true, paramType = "body", dataType = "orderSubmitDTO")
    @PostMapping("/_submit")
    public Result submit(@Valid @RequestBody OrderSubmitDTO orderSubmitDTO) {
        OrderSubmitVO result = orderService.submit(orderSubmitDTO);
        return Result.success(result);
    }

    @ApiOperation("订单列表查询")
    @GetMapping("/list")
    public Result<List<OrderListVO>> list(
            @ApiParam(name = "status", value = "订单状态", required = true, defaultValue = "0")
            @RequestParam(value = "status", defaultValue = "0") Integer status) {
        List<OrderListVO> orderList = orderService.list(status);
        return Result.success(orderList);
    }

}
