package com.youlai.mall.oms.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.mall.oms.model.query.OrderPageQuery;
import com.youlai.mall.oms.model.vo.OmsOrderPageVO;
import com.youlai.mall.oms.service.admin.OmsOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class OmsOrderServiceImplTest {

    @Autowired
    private OmsOrderService omsOrderService;

    @Test
    void testGetOrderPage() {
        OrderPageQuery queryParams = new OrderPageQuery();
        queryParams.setPageNum(1);
        queryParams.setPageSize(10);
        queryParams.setBeginDate(DateUtil.parseDate("2022-01-01"));
        queryParams.setEndDate(DateUtil.parseDate("2025-01-01"));

        IPage<OmsOrderPageVO> orderPage = omsOrderService.getOrderPage(queryParams);

        log.info(JSONUtil.toJsonStr(orderPage));

    }


}