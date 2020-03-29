package com.fly4j.shop.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.shop.order.pojo.dto.MoneyInfoDTO;
import com.fly4j.shop.order.pojo.dto.OrderDeliveryDTO;
import com.fly4j.shop.order.pojo.dto.OrderDetailDTO;
import com.fly4j.shop.order.pojo.dto.ReceiverInfoDTO;
import com.fly4j.shop.order.pojo.entity.Order;
import com.fly4j.shop.order.pojo.entity.OrderOperateHistory;
import com.fly4j.shop.order.mapper.OrderMapper;
import com.fly4j.shop.order.service.IOrderOperateHistoryService;
import com.fly4j.shop.order.service.IOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Resource
    private IOrderService iOrderService;
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private IOrderOperateHistoryService iOrderOperateHistoryService;

    public boolean deliver(List<OrderDeliveryDTO> deliveryParamList) {
        //批量发货
        boolean count = orderMapper.deliver(deliveryParamList);
        //添加操作记录
        List<OrderOperateHistory> operateHistoryList = deliveryParamList.stream()
                .map(omsOrderDeliveryParam -> {
                    OrderOperateHistory history = new OrderOperateHistory();
                    history.setOrderId(omsOrderDeliveryParam.getOrderId());
                    history.setCreateTime(new Date());
                    history.setOperateMan("后台管理员");
                    history.setOrderStatus(2);
                    history.setNote("完成发货");
                    return history;
                }).collect(Collectors.toList());
        iOrderOperateHistoryService.saveBatch(operateHistoryList);
        return count;
    }

    @Override
    public boolean close(List<Long> ids, String note) {
        Order order = new Order();
        order.setStatus(4);
        boolean status = iOrderService.update(order,new LambdaQueryWrapper<Order>()
                .eq(Order::getDeleteStatus, 0)
                .in(Order::getId,ids));
        List<OrderOperateHistory> historyList = ids.stream().map(orderId -> {
            OrderOperateHistory history = new OrderOperateHistory();
            history.setOrderId(orderId);
            history.setCreateTime(new Date());
            history.setOperateMan("后台管理员");
            history.setOrderStatus(4);
            history.setNote("订单关闭:"+note);
            return history;
        }).collect(Collectors.toList());
        iOrderOperateHistoryService.saveBatch(historyList);
        return status;
    }


    @Override
    public OrderDetailDTO getDetail(Long id) {
       return orderMapper.getDetail(id);
    }

    @Override
    public boolean updateReceiverInfo(ReceiverInfoDTO receiverInfoDto) {
        Order order = new Order();
        order.setId(receiverInfoDto.getOrderId());
        order.setReceiverName(receiverInfoDto.getReceiverName());
        order.setReceiverPhone(receiverInfoDto.getReceiverPhone());
        order.setReceiverPostCode(receiverInfoDto.getReceiverPostCode());
        order.setReceiverDetailAddress(receiverInfoDto.getReceiverDetailAddress());
        order.setReceiverProvince(receiverInfoDto.getReceiverProvince());
        order.setReceiverCity(receiverInfoDto.getReceiverCity());
        order.setReceiverRegion(receiverInfoDto.getReceiverRegion());

        boolean status=iOrderService.updateById(order);
        //插入操作记录
        OrderOperateHistory history = new OrderOperateHistory();
        history.setOrderId(receiverInfoDto.getOrderId());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(receiverInfoDto.getStatus());
        history.setNote("修改收货人信息");
        iOrderOperateHistoryService.save(history);
        return status;
    }

    @Override
    public boolean updateMoneyInfo(MoneyInfoDTO moneyInfoDto) {
        Order order = new Order();
        order.setId(moneyInfoDto.getOrderId());
        order.setFreightAmount(moneyInfoDto.getFreightAmount());
        order.setDiscountAmount(moneyInfoDto.getDiscountAmount());
        boolean status=iOrderService.updateById(order);
        //插入操作记录
        OrderOperateHistory history = new OrderOperateHistory();
        history.setOrderId(moneyInfoDto.getOrderId());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(moneyInfoDto.getStatus());
        history.setNote("修改费用信息");
        iOrderOperateHistoryService.save(history);
        return status;
    }

    @Override
    public boolean updateNote(Long id, String note, Integer status) {
        Order order = new Order();
        order.setId(id);
        order.setNote(note);
        boolean count=iOrderService.updateById(order);
        OrderOperateHistory history = new OrderOperateHistory();
        history.setOrderId(id);
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(status);
        history.setNote("修改备注信息："+note);
        iOrderOperateHistoryService.save(history);
        return count;
    }

}
