
package com.youlai.mall.oms.converter;

import com.youlai.mall.oms.model.entity.OmsOrderItem;
import com.youlai.mall.oms.model.form.OrderSubmitForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;


/**
 * 订单商品明细对象转化器
 *
 * @author haoxr
 * @since 2.0.0
 */
@Mapper(componentModel = "spring")
public interface OrderItemConverter {

    @Mappings({
            @Mapping(target = "totalAmount", expression = "java(item.getPrice() * item.getQuantity())"),
    })
    OmsOrderItem item2Entity(OrderSubmitForm.OrderItem item);

    List<OmsOrderItem> item2Entity(List<OrderSubmitForm.OrderItem> list);

}