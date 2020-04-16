package com.fly4j.yshop.oms.feign;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.yshop.pms.feign.PmsFeign;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author fly2021【xianrui0365@163.com】
 * @date 2020-04-16 10:12
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PmsFeignTests {

    @Autowired
    private PmsFeign pmsFeign;

}
