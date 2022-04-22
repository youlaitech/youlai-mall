package com.youlai.mall.oms.api;

import com.youlai.common.result.Result;
import com.youlai.mall.oms.dto.OrderInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 订单Feign客户端
 *
 * @author haoxr
 * @createTime 2021/3/13 11:59
 */
@FeignClient(value = "mall-oms", contextId = "order")
public interface OrderFeignClient {


    /**
     * 「实验室」订单状态修改
     *
     * @param orderId 订单ID
     * @param status  订单状态
     * @param orderEx 订单异常
     * @return
     */
    @PutMapping("/api/v1/orders/{orderId}/status")
    Result updateOrderStatus(@PathVariable Long orderId, @RequestParam Integer status, @RequestParam Boolean orderEx);

    /**
     * 「实验室」获取订单信息
     *
     * @param orderId
     * @return
     */
    @GetMapping("/api/v1/orders/{orderId}/info")
    Result<OrderInfoDTO> getOrderInfo(@PathVariable Long orderId);

}
