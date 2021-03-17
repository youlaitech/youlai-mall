package com.youlai.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.youlai.mall.oms.pojo.domain.OmsOrder;
import com.youlai.mall.oms.pojo.dto.OrderConfirmDTO;
import com.youlai.mall.oms.pojo.vo.OrderConfirmVO;
import com.youlai.mall.oms.pojo.vo.OrderListVO;
import com.youlai.mall.oms.pojo.vo.OrderSubmitVO;
import com.youlai.mall.oms.pojo.dto.OrderSubmitDTO;

import java.util.List;

/**
 * 订单详情表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
public interface IOrderService extends IService<OmsOrder> {

    /**
     * 订单确认
     */
    OrderConfirmVO confirm(OrderConfirmDTO orderConfirmDTO);

    /**
     * 提交订单
     */
    OrderSubmitVO submit(OrderSubmitDTO orderSubmitDTO) ;




    /**
     * 系统关闭订单
     */
    boolean closeOrder(Long orderId);

    /**
     * 取消订单接口
     */
    boolean cancelOrder(Long id);

    /**
     * 删除订单
     */
    boolean deleteOrder(Long id);

    /**
     * 订单列表查询
     */
    List<OrderListVO> list(Integer status);

    /**
     * 根据订单ID获取订单信息
     */
    OmsOrder getByOrderId(Long id);
}

