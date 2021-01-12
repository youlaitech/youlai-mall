package com.youlai.mall.oms.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.common.core.result.ResultCode;
import com.youlai.mall.oms.controller.admin.AdminOrderController;
import com.youlai.mall.oms.pojo.OmsOrder;
import com.youlai.mall.oms.service.IOmsOrderService;
import com.youlai.mall.pms.api.ProductFeignService;
import com.youlai.mall.ums.api.MemberFeignService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class AdminOrderControllerTest {

    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public AdminOrderController adminOrderController;


    /**
     * 提交订单
     *
     * @throws Exception
     */
    @Test
    public void saveOrder() throws Exception {

        String goods = "{\"order\":{\"userId\":0,\"status\":10,\"source\":0,\"consignee\":\"str\",\"mobile\":\"str\",\"postcode\":\"str\",\"address\":\"str\",\"couponId\":0,\"skuPrice\":0,\"freightPrice\":0,\"couponPrice\":0,\"orderPrice\":0,\"integrationPrice\":0,\"payPrice\":0,\"payId\":\"str\",\"payType\":0,\"payTime\":1606379283562,\"shipSn\":\"str\",\"shipChannel\":\"str\"},\n" +
                "        \"orderItems\":[{\"spuId\":0,\"spuName\":\"str\",\"skuId\":\"0\",\"skuBarCode\":\"str\",\"skuSpecifications\":\"str\",\"skuPrice\":0,\"skuQuantity\":0,\"pic\":\"str\"}]}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/orders")
                .contentType("application/json")
                .content(goods))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResultCode.SUCCESS.getCode()))
                .andDo(print())
                .andReturn();

        log.info(result.getResponse().getContentAsString());
    }


    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private MemberFeignService memberFeignService;

    @Autowired
    private IOmsOrderService iOmsOrderService;


    @Test
    @GlobalTransactional(rollbackFor = Exception.class)
    public void submitOrder() {
        // 扣减库存
        productFeignService.updateStock(151l, -1);
        // 增加积分
        memberFeignService.updatePoint(1l, 10);
        // 修改订单状态
        iOmsOrderService.update(new LambdaUpdateWrapper<OmsOrder>().eq(OmsOrder::getId, 1l).set(OmsOrder::getStatus, 901));
    }
}
