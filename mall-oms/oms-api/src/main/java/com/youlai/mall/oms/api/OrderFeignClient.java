package com.youlai.mall.oms.api;

import com.youlai.common.result.Result;
import com.youlai.mall.oms.dto.OrderInfoDTO;
import com.youlai.mall.oms.dto.SeataOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 订单 Feign Client
 *
 * @author haoxr
 * @date 2021/3/13
 */
@FeignClient(value = "mall-oms", contextId = "order")
public interface OrderFeignClient {

    /**
     * 「实验室」获取订单信息
     *
     * @param orderId
     * @return
     */
    @GetMapping("/api/v1/orders/{orderId}/orderInfo")
    Result<OrderInfoDTO> getOrderInfo(@PathVariable Long orderId);

    /**
     * 「实验室」订单支付
     *
     * @param orderDTO
     * @return
     */
    @PutMapping("/api/v1/orders/{orderId}/_pay")
    Result<String> payOrder(@PathVariable Long orderId, @RequestBody SeataOrderDTO orderDTO);

    /**
     * 「实验室」订单重置
     */
    @PutMapping("/api/v1/orders/{orderId}/_reset")
    Result resetOrder(@PathVariable Long orderId);

}
