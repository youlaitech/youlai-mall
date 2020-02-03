package com.fly.cloud.seckill;


import com.fly.cloud.seckill.mapper.SeckillMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeckillServiceApplication.class)

public class SeckillMapperTest {

    @Resource
    private SeckillMapper seckillMapper;


    @Test
    public void testReduceStock(){
        seckillMapper.reduceStock(1,new Date());
    }




}
