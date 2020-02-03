package com.fly.cloud.seckill;


import com.fly.cloud.seckill.mapper.SeckillOrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeckillServiceApplication.class)

public class SeckillOrderMapperTest {

    @Resource
    private SeckillOrderMapper seckillOrderMapper;

    @Test
    public void testInsertOrder(){
        int i = seckillOrderMapper.insertOrder(2, new BigDecimal(9.9), 15656435641L);
        System.out.println(i);
    }






}
