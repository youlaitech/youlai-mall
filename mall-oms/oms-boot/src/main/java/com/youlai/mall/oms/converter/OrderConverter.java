package com.youlai.mall.oms.converter;

import com.youlai.mall.oms.pojo.entity.OmsOrder;
import com.youlai.mall.oms.pojo.form.OrderSubmitForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


/**
 * 订单转化器
 *
 * @author haoxr
 * @date 2022/12/21
 */
@Mapper(componentModel = "spring")
public interface OrderConverter {

    @Mappings({
            @Mapping(target = "orderSn", source = "orderToken"),
            @Mapping(target = "totalQuantity", expression = "java(orderSubmitForm.getOrderItems().stream().map(OrderItemDTO::getCount).reduce(0, Integer::sum))"),
            @Mapping(target = "totalAmount", expression = "java(orderSubmitForm.getOrderItems().stream().map(item -> item.getPrice() * item.getCount()).reduce(0L, Long::sum))"),
    })
    OmsOrder submitForm2Entity(OrderSubmitForm orderSubmitForm);
}