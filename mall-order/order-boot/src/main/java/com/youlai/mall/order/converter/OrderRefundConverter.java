package com.youlai.mall.order.converter;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.mall.order.model.entity.OrderRefund;
import com.youlai.mall.order.model.form.OrderRefundForm;
import com.youlai.mall.order.model.bo.OrderRefundBO;

/**
 * 转换器
 *
 * @author Ray Hao
 * @since 2024-06-07
 */
@Mapper(componentModel = "spring")
public interface OrderRefundConverter{

    OrderRefundPageVO bo2PageVo(OrderRefundBO bo);

    Page<OrderRefundPageVO> bo2PageVo(Page<OrderRefundBO> bo);

    OrderRefundForm entity2Form(OrderRefund entity);

    @InheritInverseConfiguration(name = "entity2Form")
    OrderRefund convertToEntity(OrderRefundForm entity);
}