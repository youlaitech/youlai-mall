package com.youlai.mall.pms.controller;

import com.youlai.common.result.ResultCode;
import com.youlai.mall.pms.pojo.bo.app.ProductBO;
import com.youlai.mall.pms.controller.admin.SpuController;
import com.youlai.mall.pms.pojo.domain.PmsSpec;
import com.youlai.mall.pms.service.IPmsSpuAttributeValueService;
import com.youlai.mall.pms.service.IPmsSpecService;
import com.youlai.mall.pms.service.IPmsSpuService;
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class ProductControllerTest {

    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public SpuController spuController;


    @Test
    public void saveGoods() throws Exception {

        String goods = "{\"spu\":{\"name\":\"小米手机10\",\"categoryId\":\"8\",\"brandId\":\"1\",\"originPrice\":599900,\"price\":499900,\"pic\":\"http://101.37.69.49:9000/default/40ffc46040ca431aba23c48798a82bb8.jpg\",\"album\":\"[\\\"http://101.37.69.49:9000/default/dbb1c4e37b6244f3a6b0f635db90bf54.jpg\\\"]\",\"unit\":\"台\",\"description\":\"商品简介\",\"detail\":\"<p>商品详情</p>\",\"status\":1},\"attributes\":[{\"name\":\"上市时间\",\"value\":\"2020-10-10\"}],\"specifications\":[{\"name\":\"颜色\",\"value\":\"黑色\"},{\"name\":\"颜色\",\"value\":\"白色\"},{\"name\":\"内存\",\"value\":\"4G\"},{\"name\":\"内存\",\"value\":\"6G\"},{\"name\":\"存储\",\"value\":\"64G\"},{\"name\":\"存储\",\"value\":\"128G\"}],\"skuList\":[{\"颜色\":\"黑色\",\"内存\":\"4G\",\"存储\":\"64G\",\"price\":401,\"originPrice\":1,\"stock\":2,\"pic\":\"http://101.37.69.49:9000/default/d7c36e289eb14dcea67d20ebcac79d87.jpg\",\"barCode\":\"1605317058485\",\"specification\":\"{\\\"颜色\\\":\\\"黑色\\\",\\\"内存\\\":\\\"4G\\\",\\\"存储\\\":\\\"64G\\\"}\"},{\"颜色\":\"黑色\",\"内存\":\"4G\",\"存储\":\"128G\",\"price\":301,\"originPrice\":1,\"stock\":1,\"pic\":\"http://101.37.69.49:9000/default/29697a3f43f64172b91b4d1d241ca602.jpg\",\"barCode\":\"1605317059016\",\"specification\":\"{\\\"颜色\\\":\\\"黑色\\\",\\\"内存\\\":\\\"4G\\\",\\\"存储\\\":\\\"128G\\\"}\"},{\"颜色\":\"黑色\",\"内存\":\"6G\",\"存储\":\"64G\",\"price\":200.99999999999997,\"originPrice\":1,\"stock\":1,\"pic\":\"http://101.37.69.49:9000/default/d4b46f2405b54635bb1c0589f68a74e6.png\",\"barCode\":\"1605317059753\",\"specification\":\"{\\\"颜色\\\":\\\"黑色\\\",\\\"内存\\\":\\\"6G\\\",\\\"存储\\\":\\\"64G\\\"}\"},{\"颜色\":\"黑色\",\"内存\":\"6G\",\"存储\":\"128G\",\"price\":301,\"originPrice\":1,\"stock\":1,\"pic\":\"http://101.37.69.49:9000/default/432579106d32465296f930d15eafd466.png\",\"barCode\":\"1605317060895\",\"specification\":\"{\\\"颜色\\\":\\\"黑色\\\",\\\"内存\\\":\\\"6G\\\",\\\"存储\\\":\\\"128G\\\"}\"},{\"颜色\":\"白色\",\"内存\":\"4G\",\"存储\":\"64G\",\"price\":200.99999999999997,\"originPrice\":2.01,\"stock\":1,\"pic\":\"http://101.37.69.49:9000/default/f5eb5e307adf439cb7da0f847f4ddace.png\",\"barCode\":\"1605317061416\",\"specification\":\"{\\\"颜色\\\":\\\"白色\\\",\\\"内存\\\":\\\"4G\\\",\\\"存储\\\":\\\"64G\\\"}\"},{\"颜色\":\"白色\",\"内存\":\"4G\",\"存储\":\"128G\",\"price\":200.99999999999997,\"originPrice\":1.01,\"stock\":1,\"pic\":\"http://101.37.69.49:9000/default/9de00b77c06245538572c09ad689dfda.jpg\",\"specification\":\"{\\\"颜色\\\":\\\"白色\\\",\\\"内存\\\":\\\"4G\\\",\\\"存储\\\":\\\"128G\\\"}\"},{\"颜色\":\"白色\",\"内存\":\"6G\",\"存储\":\"64G\",\"price\":200.99999999999997,\"originPrice\":1.01,\"stock\":1,\"pic\":\"http://101.37.69.49:9000/default/d48ac97541f44cea8087b8f26da588c4.jpg\",\"barCode\":\"1605317062900\",\"specification\":\"{\\\"颜色\\\":\\\"白色\\\",\\\"内存\\\":\\\"6G\\\",\\\"存储\\\":\\\"64G\\\"}\"},{\"颜色\":\"白色\",\"内存\":\"6G\",\"存储\":\"128G\",\"price\":301,\"originPrice\":0.01,\"stock\":1,\"pic\":\"http://101.37.69.49:9000/default/9b2a4dfae67b44b89cc9589de691ee8d.jpg\",\"barCode\":\"1605317063290\",\"specification\":\"{\\\"颜色\\\":\\\"白色\\\",\\\"内存\\\":\\\"6G\\\",\\\"存储\\\":\\\"128G\\\"}\"}]}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/goods")
                .contentType("application/json")
                .content(goods))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResultCode.SUCCESS.getCode()))
                .andDo(print())
                .andReturn();

        log.info(result.getResponse().getContentAsString());

    }

    @Autowired
    public IPmsSpecService iPmsSpecService;

    @Test
    public void getProductSpecList() {
        List<PmsSpec> specifications = iPmsSpecService.listBySpuId(1l);
        log.info(specifications.toString());
    }

    @Autowired
    public IPmsSpuAttributeValueService iPmsSpuAttributeValueService;



    @Autowired
    private IPmsSpuService iPmsSpuService;

    @Test
    public void getProduct() {
        ProductBO product = iPmsSpuService.getProductByIdForApp(1l);
        log.info(product.toString());
    }
}
