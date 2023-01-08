
package com.youlai.mall.oms.converter;

import cn.hutool.core.collection.CollectionUtil;
import com.youlai.mall.oms.pojo.dto.OrderItemDTO;
import com.youlai.mall.oms.pojo.entity.OmsOrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 订单对象转化器
 *
 * @author haoxr
 * @date 2022/12/21
 */
@Mapper(componentModel = "spring")
public interface OrderItemConverter {

    @Mappings({
            @Mapping(target = "totalAmount", expression = "java(dto.getPrice() * dto.getCount())"),
            @Mapping(target = "orderId", source = "orderId"),
    })
    OmsOrderItem dto2Entity(Long orderId, OrderItemDTO dto);


    default List<OmsOrderItem> dto2Entity(Long orderId, List<OrderItemDTO> list) {
        if (CollectionUtil.isNotEmpty(list)) {
            List<OmsOrderItem> entities = list.stream().map(dto -> dto2Entity(orderId, dto))
                    .collect(Collectors.toList());
            return entities;
        }
        return Collections.EMPTY_LIST;
    }
}