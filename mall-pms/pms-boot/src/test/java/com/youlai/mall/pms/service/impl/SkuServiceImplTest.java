package com.youlai.mall.pms.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.youlai.mall.pms.model.dto.LockSkuDTO;
import com.youlai.mall.pms.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class SkuServiceImplTest {

    @Autowired
    SkuService skuService;

    /**
     * 模拟并发锁定库存
     */
    @Test
    void lockStock() throws InterruptedException {
        TimeInterval timer = DateUtil.timer();

        int numberOfThreads = 50;
        CountDownLatch countDownLatch = new CountDownLatch(numberOfThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        List<LockSkuDTO> lockedSkuList = Arrays.asList(new LockSkuDTO(1L, 1));

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    skuService.lockStock("20231122000001", lockedSkuList);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();  // Wait for all threads to finish
        executorService.shutdown();
        log.info("锁定商品库存耗时:{}ms", timer.interval());
    }


    @Test
    void unlockStock() {
    }

    @Test
    void deductStock() {
    }
}