package com.youlai.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.youlai.mall.oms.pojo.entity.OrderEntity;
import com.youlai.mall.oms.pojo.vo.OrderConfirmVO;
import com.youlai.mall.oms.pojo.vo.OrderListVO;
import com.youlai.mall.oms.pojo.vo.OrderSubmitResultVO;
import com.youlai.mall.oms.pojo.vo.OrderSubmitVO;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 订单详情表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
public interface OrderService extends IService<OrderEntity> {

    /**
     * 订单确认页信息
     *
     * @param skuId  商品id，非必填参数
     * @param number 商品数量
     * @return
     */
    OrderConfirmVO confirm(String skuId, Integer number);

    /**
     * 提交订单
     *
     * @param submit 订单提交参数
     */
    OrderSubmitResultVO submit(OrderSubmitVO submit) throws ExecutionException, InterruptedException;

    /**
     * 根据订单号查询订单详情
     *
     * @param orderSn 订单号
     * @return
     */
    OrderEntity getByOrderSn(String orderSn);

    /**
     * 系统关闭订单
     *
     * @param orderSn 订单号
     */
    boolean closeOrderBySystem(String orderSn);

    /**
     * 取消订单接口
     *
     * @param id 订单ID
     * @return 是否取消成功
     */
    boolean cancelOrder(String id);

    /**
     * 删除订单
     *
     * @param id 订单ID
     * @return 是否删除成功
     */
    boolean deleteOrder(String id);

    /**
     * 订单列表查询
     *
     * @param status 订单状态
     * @return 订单列表
     */
    List<OrderListVO> list(Integer status);

    /**
     * 根据订单ID获取订单信息
     *
     * @param id 订单ID
     * @return 订单信息
     */
    OrderEntity getByOrderId(String id);
}

