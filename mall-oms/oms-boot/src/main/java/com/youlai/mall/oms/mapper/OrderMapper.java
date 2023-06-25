package com.youlai.mall.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.oms.model.entity.OmsOrder;
import com.youlai.mall.oms.model.query.OrderPageQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 订单表
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
    List<OmsOrder> listOrderPages(Page<OmsOrder> page, OrderPageQuery queryParams);
}
