package com.youlai.mall.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.oms.pojo.entity.OmsOrder;
import com.youlai.mall.oms.pojo.query.OrderPageQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 订单详情表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
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
