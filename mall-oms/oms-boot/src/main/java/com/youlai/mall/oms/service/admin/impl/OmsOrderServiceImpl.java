package com.youlai.mall.oms.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.redis.BusinessSnGenerator;
import com.youlai.mall.oms.common.enums.OrderStatusEnum;
import com.youlai.mall.oms.dto.SeataOrderDTO;
import com.youlai.mall.oms.mapper.OrderMapper;
import com.youlai.mall.oms.model.entity.OmsOrder;
import com.youlai.mall.oms.model.query.OrderPageQuery;
import com.youlai.mall.oms.service.admin.OmsOrderService;
import com.youlai.mall.ums.api.MemberFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 「管理端」订单业务实现类
 *
 * @author haoxr
 * @since 2022/2/12
 */
@Service
@RequiredArgsConstructor
public class OmsOrderServiceImpl extends ServiceImpl<OrderMapper, OmsOrder> implements OmsOrderService {

    /**
     * 订单分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public IPage<OmsOrder> listOrderPages(OrderPageQuery queryParams) {
        Page<OmsOrder> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<OmsOrder> list = this.baseMapper.listOrderPages(page, queryParams);
        page.setRecords(list);
        return page;
    }

}
