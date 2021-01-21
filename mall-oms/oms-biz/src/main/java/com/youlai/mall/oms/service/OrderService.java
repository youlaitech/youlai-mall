package com.youlai.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.pojo.entity.OrderEntity;
import com.youlai.mall.oms.pojo.vo.OrderConfirmVO;
import com.youlai.mall.oms.pojo.vo.OrderSubmitVO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 订单详情表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 订单确认页信息
     *
     * @return
     */
    OrderConfirmVO confirm();

    /**
     * 提交订单
     *
     * @param submit 订单提交参数
     */
    void submit(OrderSubmitVO submit) throws ExecutionException, InterruptedException;

}

