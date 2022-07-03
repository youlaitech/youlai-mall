package com.youlai.mall.pms.service.impl;

import com.youlai.mall.pms.pojo.vo.PmsSpuDetailVO;
import com.youlai.mall.pms.service.IPmsSpuService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author haoxr
 * @date 2021/7/17
 */
@SpringBootTest
@Slf4j
class PmsSpuServiceImplTest {

    @Autowired
    private IPmsSpuService iPmsSpuService;

    @Test
    void getPmsSpuDetail() {
        PmsSpuDetailVO goodsDetail = iPmsSpuService.getPmsSpuDetail(1l);
        log.info(goodsDetail.toString());
    }
}