package com.youlai.mall.oms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.oms.bo.OrderBO;
import com.youlai.mall.oms.pojo.OmsOrder;
import com.youlai.mall.oms.mapper.OmsOrderMapper;
import com.youlai.mall.oms.pojo.OmsOrderItem;
import com.youlai.mall.oms.service.IOmsOrderService;
import com.youlai.mall.oms.service.IOmsOrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements IOmsOrderService {




    private IOmsOrderItemService iOmsOrderItemService;

    /**
     * 提交订单
     *
     * @param orderBO
     * @return
     */
    @Override
    public boolean save(OrderBO orderBO) {
        // 订单
        OmsOrder order = orderBO.getOrder();
        String orderSn = IdUtil.createSnowflake(1, 1).nextIdStr();
        order.setOrderSn(orderSn);
        this.save(order);

        // 订单明细
        List<OmsOrderItem> orderItems = orderBO.getOrderItems();
        if (CollectionUtil.isEmpty(orderItems)) {
            throw new BizException("订单明细不能为空");
        }
        orderItems.forEach(item -> {
            item.setOrderId(order.getId());
            item.setOrderSn(orderSn);
        });
        iOmsOrderItemService.saveBatch(orderItems);
        return true;
    }
}
