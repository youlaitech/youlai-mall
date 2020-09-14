package com.youlai.admin;

import cn.hutool.core.lang.Assert;
import com.youlai.admin.service.ISysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AdminApplicationTests {

    @Autowired
    private ISysMenuService iSysMenuService;

    @Test
    public void testListForRouter() {
        List list = iSysMenuService.listForRouter();
        Assert.isTrue(list.size()>0);
    }
}
