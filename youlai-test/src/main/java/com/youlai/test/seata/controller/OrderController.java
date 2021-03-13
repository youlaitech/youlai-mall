package com.youlai.test.seata.controller;

import com.youlai.common.result.Result;
import com.youlai.test.seata.dto.OrderDTO;
import com.youlai.test.seata.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author haoxr
 * @description 订单提交全局事务一致性测试 1.扣減庫存 2. 减账户余额 3.更改订单状态已完成
 * @createTime 2021/3/13 11:10
 */
@Api(tags = "【Seata】订单提交")
@RequestMapping("/api.admin/v1/orders")
@Slf4j
@AllArgsConstructor
public class OrderController {

    private IOrderService orderService;

    @ApiOperation(value = "订单提交", httpMethod = "POST")
    @PostMapping
    public Result submit(OrderDTO order) {
        boolean status;
        if (order.getOpenTransaction()) {
            status = orderService.saveWithGlobalTransactional(order);
        } else {
            status = orderService.save(order);
        }
        return Result.judge(status);
    }

}
