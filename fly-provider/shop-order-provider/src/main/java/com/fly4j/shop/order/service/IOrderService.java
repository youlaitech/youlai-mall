package com.fly4j.shop.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.shop.order.pojo.dto.MoneyInfoDTO;
import com.fly4j.shop.order.pojo.dto.OrderDeliveryDTO;
import com.fly4j.shop.order.pojo.dto.OrderDetailDTO;
import com.fly4j.shop.order.pojo.dto.ReceiverInfoDTO;
import com.fly4j.shop.order.pojo.entity.Order;

import java.util.List;

public interface IOrderService extends IService<Order> {

    boolean delivery(List<OrderDeliveryDTO> deliveryParamList);

    boolean close(List<Long> ids, String note);

    OrderDetailDTO detail(Long id);

    boolean updateReceiverInfo(ReceiverInfoDTO receiverInfoDto);

    boolean updateMoneyInfo(MoneyInfoDTO moneyInfoDto);

    boolean updateNote(Long id, String note, Integer status);
}
