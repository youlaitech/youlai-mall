package com.youlai.mall.order.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.order.model.bo.OrderBO;
import com.youlai.mall.order.model.entity.OmsOrder;
import com.youlai.mall.order.model.form.OrderSubmitForm;
import com.youlai.mall.order.model.vo.OrderPageAdminVO;
import com.youlai.mall.order.model.vo.OrderPageAppVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


/**
 * 订单对象转化器
 *
 * @author Ray
 * @since 2.0.0
 */
@Mapper(componentModel = "spring")
public interface OrderConverter {

    @Mappings({
            @Mapping(target = "orderNo", source = "orderToken"),
            @Mapping(target = "totalFee",
                    expression = "java(orderSubmitForm.getOrderItems().stream().map(item -> item.getSkuPrice() * item.getSkuQuantity()).reduce(0L, Long::sum))"),
    })
    OmsOrder convertToEntity(OrderSubmitForm orderSubmitForm);

    @Mappings({
            @Mapping(
                    target = "paymentMethodLabel",
                    expression = "java(com.youlai.common.base.IBaseEnum.getLabelByValue(bo.getPaymentMethod(), com.youlai.mall.order.enums.PaymentMethodEnum.class))"
            ),
            @Mapping(
                    target = "orderItems",
                    source = "orderItems"
            )
    })
    OrderPageAdminVO toPageVo(OrderBO bo);

    Page<OrderPageAdminVO> toPageVo(Page<OrderBO> boPage);

    OrderPageAdminVO.OrderItem toPageVoOrderItem(OrderBO.OrderItem orderItem);


    @Mappings({
            @Mapping(
                    target = "paymentMethodLabel",
                    expression = "java(com.youlai.common.base.IBaseEnum.getLabelByValue(bo.getPaymentMethod(), com.youlai.mall.order.enums.PaymentMethodEnum.class))"
            ),
            @Mapping(
                    target = "sourceLabel",
                    expression = "java(com.youlai.common.base.IBaseEnum.getLabelByValue(bo.getSource(), com.youlai.mall.order.enums.OrderSourceEnum.class))"
            ),
            @Mapping(
                    target = "statusLabel",
                    expression = "java(com.youlai.common.base.IBaseEnum.getLabelByValue(bo.getStatus(), com.youlai.mall.order.enums.OrderStatusEnum.class))"
            ),
            @Mapping(
                    target = "orderItems",
                    source = "orderItems"
            )
    })
    OrderPageAppVO toAppPageVo(OrderBO bo);

    Page<OrderPageAppVO> toAppPageVo(Page<OrderBO> boPage);

    OrderPageAppVO.OrderItem toPageVoOrderItemForApp(OrderBO.OrderItem orderItem);
}