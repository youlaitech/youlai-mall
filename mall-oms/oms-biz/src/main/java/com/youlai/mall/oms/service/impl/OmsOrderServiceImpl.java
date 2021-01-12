package com.youlai.mall.oms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.core.result.Result;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.oms.bo.OrderBO;
import com.youlai.mall.oms.mapper.OmsOrderMapper;
import com.youlai.mall.oms.pojo.OmsOrder;
import com.youlai.mall.oms.pojo.OmsOrderItem;
import com.youlai.mall.oms.service.IOmsOrderItemService;
import com.youlai.mall.oms.service.IOmsOrderService;
import com.youlai.mall.pms.api.ProductFeignService;
import com.youlai.mall.ums.api.MemberFeignService;
import com.youlai.mall.ums.pojo.dto.MemberDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements IOmsOrderService {

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
        });
        iOmsOrderItemService.saveBatch(orderItems);
        return true;
    }

    @Override
    public OrderBO getByOrderId(Long orderId) {
        OrderBO orderBO = new OrderBO();
        // 订单
        OmsOrder order = this.getById(orderId);
        if (order == null) {
            throw new BizException("订单不存在");
        }
        // 订单明细
        List<OmsOrderItem> orderItems = iOmsOrderItemService.list(
                new LambdaQueryWrapper<OmsOrderItem>().eq(OmsOrderItem::getOrderId, orderId)
        );
        orderItems = Optional.ofNullable(orderItems).orElse(new ArrayList<>());

        // 会员明细
        Result<MemberDTO> result = memberFeignService.getUserById(order.getUserId());
        MemberDTO member = result.getData();
        orderBO.setOrder(order).setOrderItems(orderItems).setMember(member);
        return orderBO;
    }

    private ProductFeignService productFeignService;

    @Override
    public boolean submit() {
        log.info("扣减库存----begin");
        productFeignService.updateStock(1l, -1);
        log.info("扣减库存----end");

        log.info("增加积分----begin");
        memberFeignService.updatePoint(1l, 10);
        log.info("增加积分----end");

        log.info("修改订单状态----begin");
        boolean result = this.update(new LambdaUpdateWrapper<OmsOrder>().eq(OmsOrder::getId, 1l).set(OmsOrder::getStatus, 901));
        log.info("修改订单状态----end");
        return result;
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public boolean submitWithGlobalTransactional() {
        log.info("扣减库存----begin");
        productFeignService.updateStock(1l, -1);
        log.info("扣减库存----end");

        log.info("增加积分----begin");
        memberFeignService.updatePoint(1l, 10);
        log.info("增加积分----end");

        log.info("修改订单状态----begin");
        boolean result = this.update(new LambdaUpdateWrapper<OmsOrder>().eq(OmsOrder::getId, 1l).set(OmsOrder::getStatus, 901));
        log.info("修改订单状态----end");
        return result;
    }

}
