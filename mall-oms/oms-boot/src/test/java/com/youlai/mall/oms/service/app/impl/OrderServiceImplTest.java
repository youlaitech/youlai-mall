package com.youlai.mall.oms.service.app.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.mall.oms.model.query.OrderPageQuery;
import com.youlai.mall.oms.model.vo.OrderPageVO;
import com.youlai.mall.oms.service.app.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Test
    void testGetOrderPage() {
        OrderPageQuery queryParams = new OrderPageQuery();
        queryParams.setPageNum(1);
        queryParams.setPageSize(10);
        queryParams.setBeginDate(DateUtil.parseDate("2022-01-01"));
        queryParams.setEndDate(DateUtil.parseDate("2025-01-01"));

        IPage<OrderPageVO> orderPage = orderService.getOrderPage(queryParams);

        log.info(JSONUtil.toJsonStr(orderPage));

    }

}