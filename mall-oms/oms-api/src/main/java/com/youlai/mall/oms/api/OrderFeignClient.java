package com.youlai.mall.oms.api;

import com.youlai.common.result.Result;
import com.youlai.mall.oms.dto.OrderInfoDTO;
import com.youlai.mall.oms.dto.SeataOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 订单Feign客户端
 *
 * @author haoxr
 * @createTime 2021/3/13 11:59
 */
@FeignClient(value = "mall-oms", contextId = "order")
public interface OrderFeignClient {

    /**
     * 「实验室」获取订单信息
     *
     * @param orderSn
     * @return
     */
    @GetMapping("/api/v1/orders/orderSn/{orderSn}")
    Result<OrderInfoDTO> getOrderInfo(@PathVariable String orderSn);

    /**
     * 「实验室」创建订单
     *
     * @param orderDTO
     * @param openEx 是否出现异常
     * @return
     */
    @PostMapping("/api/v1/orders")
    Result<String> createOrder(@RequestBody SeataOrderDTO orderDTO, @RequestParam boolean openEx);


    /**
     * 「实验室」删除订单
     */
    @DeleteMapping("/api/v1/orders/orderSn/{orderSn}")
    Result deleteOrder(@PathVariable String orderSn);

}
