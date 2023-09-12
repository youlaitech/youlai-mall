package com.youlai.mall.oms.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.oms.model.bo.OrderBO;
import com.youlai.mall.oms.model.entity.OmsOrder;
import com.youlai.mall.oms.model.form.OrderSubmitForm;
import com.youlai.mall.oms.model.vo.OmsOrderPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;


/**
 * 订单对象转化器
 *
 * @author haoxr
 * @since 2.0.0
 */
@Mapper(componentModel = "spring")
public interface OrderConverter {

    @Mappings({
            @Mapping(target = "orderSn", source = "orderToken"),
            @Mapping(target = "totalQuantity",
                    expression = "java(orderSubmitForm.getOrderItems().stream().map(OrderSubmitForm.OrderItem::getCount).reduce(0, Integer::sum))"),
            @Mapping(target = "totalAmount",
                    expression = "java(orderSubmitForm.getOrderItems().stream().map(item -> item.getPrice() * item.getCount()).reduce(0L, Long::sum))"),
    })
    OmsOrder form2Entity(OrderSubmitForm orderSubmitForm);

    @Mappings({
            @Mapping(
                    target = "paymentMethodLabel",
                    expression = "java(com.youlai.common.base.IBaseEnum.getLabelByValue(bo.getPaymentMethod(), com.youlai.mall.oms.enums.PaymentMethodEnum.class))"
            ),
            @Mapping(
                    target = "sourceLabel",
                    expression = "java(com.youlai.common.base.IBaseEnum.getLabelByValue(bo.getSource(), com.youlai.mall.oms.enums.OrderSourceEnum.class))"
            ),
            @Mapping(
                    target = "statusLabel",
                    expression = "java(com.youlai.common.base.IBaseEnum.getLabelByValue(bo.getStatus(), com.youlai.mall.oms.enums.OrderStatusEnum.class))"
            ),
            @Mapping(
                    target = "orderItems",
                    source = "orderItems"
            )
    })
    OmsOrderPageVO toVoPage(OrderBO bo);

    Page<OmsOrderPageVO> toVoPage(Page<OrderBO> boPage);

    OmsOrderPageVO.OrderItem toVoPageOrderItem(OrderBO.OrderItem orderItem);
    List<OmsOrderPageVO.OrderItem> toVoPageOrderItems(List<OrderBO.OrderItem> orderItems);
}