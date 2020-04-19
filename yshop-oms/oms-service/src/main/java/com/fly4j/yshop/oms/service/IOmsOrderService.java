package com.fly4j.yshop.oms.service;



import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.yshop.oms.pojo.dto.OrderDTO;
import com.fly4j.yshop.oms.pojo.entity.OmsOrder;


public interface IOmsOrderService extends IService<OmsOrder> {

    R submit(OrderDTO orderDTO);

    String token();

    int closeOrder(String orderToken);
}
