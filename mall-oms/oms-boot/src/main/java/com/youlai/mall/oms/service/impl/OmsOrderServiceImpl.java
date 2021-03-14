package com.youlai.mall.oms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.result.Result;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.oms.mapper.OmsOrderMapper;
import com.youlai.mall.ums.api.app.MemberFeignService;
import com.youlai.mall.ums.pojo.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder1> implements IOmsOrderService {

    private IOmsOrderItemService iOmsOrderItemService;

    private MemberFeignService memberFeignService;
    /**
     * 提交订单
     *
     * @param orderBO
     * @return
     */
    @Override
    public boolean save(OrderBO orderBO) {
        // 订单
        OmsOrder1 order = orderBO.getOrder();
        String orderSn = IdUtil.createSnowflake(1, 1).nextIdStr();
        order.setOrderSn(orderSn);
        this.save(order);

        // 订单明细
        List<OmsOrderItem1> orderItems = orderBO.getOrderItems();
        if (CollectionUtil.isEmpty(orderItems)) {
            throw new BizException("订单明细不能为空");
        }
        orderItems.forEach(item -> {
            item.setOrderId(order.getId());
        });
        iOmsOrderItemService.saveBatch(orderItems);
        return true;
    }

    @Override
    public OrderBO getByOrderId(Long orderId) {
        OrderBO orderBO = new OrderBO();
        // 订单
        OmsOrder1 order = this.getById(orderId);
        if (order == null) {
            throw new BizException("订单不存在");
        }
        // 订单明细
        List<OmsOrderItem1> orderItems = iOmsOrderItemService.list(
                new LambdaQueryWrapper<OmsOrderItem1>().eq(OmsOrderItem1::getOrderId, orderId)
        );
        orderItems = Optional.ofNullable(orderItems).orElse(new ArrayList<>());

        // 会员明细
        Result<MemberDTO> result = memberFeignService.getUserById(order.getUserId());
        MemberDTO member = result.getData();
        orderBO.setOrder(order).setOrderItems(orderItems).setMember(member);
        return orderBO;
    }


}
