package com.youlai.test.seata.service.impl;

import com.youlai.mall.oms.api.OrderFeignService;
import com.youlai.mall.oms.enums.OrderStatusEnum;
import com.youlai.mall.pms.api.admin.InventoryFeignService;
import com.youlai.mall.ums.api.admin.MemberFeignService;
import com.youlai.test.seata.dto.OrderDTO;
import com.youlai.test.seata.service.IOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author haoxr
 * @description TODO
 * @createTime 2021/3/13 11:16
 */
@Slf4j
@AllArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private InventoryFeignService inventoryFeignService;

    private MemberFeignService memberFeignService;

    private OrderFeignService orderFeignService;

    @Override
    public boolean save(OrderDTO order) {
        log.info("========================扣减库存::Begin======================");
        inventoryFeignService.deductInventory(order.getSkuId(), order.getSkuNum());
        log.info("========================扣减库存::End======================");

        log.info("========================扣减账户余额::Begin======================");
        memberFeignService.deductBalance(order.getUserId(), order.getSkuNum() * order.getSkuPrice());
        log.info("========================扣减账户余额::End======================");

        log.info("========================修改订单状态::Begin======================");
        orderFeignService.updateOrderStatus(order.getOrderId(), OrderStatusEnum.FINISH.getCode());
        log.info("========================修改订单状态::End======================");

        return true;
    }

    @Override
    public boolean saveWithGlobalTransactional(OrderDTO order) {
        log.info("========================扣减库存::Begin======================");
        inventoryFeignService.deductInventory(order.getSkuId(), order.getSkuNum());
        log.info("========================扣减库存::End======================");

        log.info("========================扣减账户余额::Begin======================");
        memberFeignService.deductBalance(order.getUserId(), order.getSkuNum() * order.getSkuPrice());
        log.info("========================扣减账户余额::End======================");

        log.info("========================修改订单状态::Begin======================");
        orderFeignService.updateOrderStatus(order.getOrderId(), OrderStatusEnum.FINISH.getCode());
        log.info("========================修改订单状态::End======================");

        return true;
    }

}
