package com.youlai.mall.oms.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.common.result.ResultCode;
import com.youlai.mall.oms.controller.admin.OrderController;
import com.youlai.mall.pms.api.app.InventoryFeignService;
import com.youlai.mall.ums.api.app.MemberFeignService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class OrderControllerTest {

    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public OrderController orderController;


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
    private InventoryFeignService inventoryFeignService;

    @Autowired
    private MemberFeignService memberFeignService;

    @Autowired
    private IOmsOrderService iOmsOrderService;


    @Test
    @GlobalTransactional(rollbackFor = Exception.class)
    public void submitOrder() {
        // 扣减库存
        // skuFeignService.lockStock(151l, -1);
        // 增加积分
        memberFeignService.updatePoint(1l, 10);
        // 修改订单状态
        iOmsOrderService.update(new LambdaUpdateWrapper<OmsOrder1>().eq(OmsOrder1::getId, 1l).set(OmsOrder1::getStatus, 901));
    }
}
