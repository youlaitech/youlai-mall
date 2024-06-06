package com.youlai.mall.order.service.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.order.model.entity.OmsOrder;
import com.youlai.mall.order.model.query.OrderPageQuery;
import com.youlai.mall.order.model.vo.OmsOrderPageVO;

/**
 * Admin-订单业务接口
 *
 * @author haoxr
 * @since 2.3.0
 */
public interface OmsOrderService extends IService<OmsOrder> {
    /**
     * 订单分页列表
     *
     * @param queryParams {@link OrderPageQuery}
     * @return
     */
    IPage<OmsOrderPageVO> getOrderPage(OrderPageQuery queryParams);


}

