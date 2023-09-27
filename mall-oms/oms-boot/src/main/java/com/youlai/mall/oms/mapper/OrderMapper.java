package com.youlai.mall.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.oms.model.bo.OrderBO;
import com.youlai.mall.oms.model.entity.OmsOrder;
import com.youlai.mall.oms.model.query.OrderPageQuery;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单数据访问层
 *
 * @author huawei
 * @since 2020-12-30 22:31:10
 */
@Mapper
public interface OrderMapper extends BaseMapper<OmsOrder> {

    /**
     * 订单分页列表
     *
     * @param page
     * @param queryParams
     * @return
     */
    Page<OrderBO> getOrderPage(Page<OrderBO> page, OrderPageQuery queryParams);
}
