package com.youlai.mall.oms.controller.app;

import com.youlai.common.result.Result;
import com.youlai.mall.oms.pojo.entity.OrderEntity;
import com.youlai.mall.oms.pojo.vo.OrderConfirmVO;
import com.youlai.mall.oms.pojo.vo.OrderListVO;
import com.youlai.mall.oms.pojo.vo.OrderSubmitResultVO;
import com.youlai.mall.oms.pojo.vo.OrderSubmitVO;
import com.youlai.mall.oms.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 *
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

    private OrderService orderService;

    /**
     * 订单确认信息，生成订单
     * 如果入参传了skuId，则以当前skuId为准，商品数量默认为1
     * 如果没有传，则从购物车中获取数据
     * 如果购物车中没有数据，则返回为空
     *
     * @return
     */
    @ApiOperation(value = "订单确认信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "skuId", value = "商品ID", required = true, paramType = "param", dataType = "String"),
            @ApiImplicitParam(name = "number", value = "商品数量", required = true, defaultValue = "1", paramType = "param", dataType = "Integer")

    })
    @PostMapping("/confirm")
    public Result<OrderConfirmVO> confirm(@RequestParam(value = "skuId", required = false) String skuId,
                                          @RequestParam(value = "number", defaultValue = "1") Integer number) {
        return Result.success(orderService.confirm(skuId, number));
    }

    @ApiOperation(value = "提交订单", httpMethod = "POST")
    @ApiImplicitParam(name = "submit", value = "提交订单参数", required = true, paramType = "body", dataType = "OrderSubmitVO")
    @PostMapping("/submit")
    public Result<OrderSubmitResultVO> submit(@Valid @RequestBody OrderSubmitVO submit) throws ExecutionException, InterruptedException {
        log.info("提交订单：{}", submit);
        return Result.success(orderService.submit(submit));
    }


    /**
     * 根据订单状态查询订单列表
     * 步骤：
     * 1、入参 status 表示订单状态
     * 2、status = 0 表示查询所有订单
     * 3、已删除订单无法查询
     */
    @ApiOperation("订单列表查询")
    @GetMapping("/list")
    public Result<List<OrderListVO>> list(@ApiParam(name = "status",value = "订单状态",required = true,defaultValue = "0")
                                              @RequestParam(value = "status",required = true,defaultValue = "0") Integer status) {
        List<OrderListVO> orderList = orderService.list(status);

        return Result.success(orderList);
    }

}
