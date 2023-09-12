package com.youlai.mall.oms.service.admin.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.oms.converter.OrderConverter;
import com.youlai.mall.oms.mapper.OrderMapper;
import com.youlai.mall.oms.model.bo.OrderBO;
import com.youlai.mall.oms.model.entity.OmsOrder;
import com.youlai.mall.oms.model.query.OrderPageQuery;
import com.youlai.mall.oms.model.vo.OmsOrderPageVO;
import com.youlai.mall.oms.service.admin.OmsOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Admin-订单业务实现类
 *
 * @author haoxr
 * @since 2.3.0
 */
@Service
@RequiredArgsConstructor
public class OmsOrderServiceImpl extends ServiceImpl<OrderMapper, OmsOrder> implements OmsOrderService {

    private final OrderConverter orderConverter;


    /**
     * Admin-订单分页列表
     *
     * @param queryParams {@link OrderPageQuery}
     * @return
     */
    @Override
    public IPage<OmsOrderPageVO> getOrderPage(OrderPageQuery queryParams) {
        Page<OrderBO> boPage = this.baseMapper.getOrderPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams);

        return orderConverter.toVoPage(boPage);
    }

}
