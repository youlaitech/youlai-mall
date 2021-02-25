package com.youlai.admin;

import cn.hutool.core.lang.Assert;
import com.youlai.admin.service.ISysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class MenuTests {

    @Autowired
    private ISysMenuService iSysMenuService;

    @Test
    public void testListRouterVO() {
        List list = iSysMenuService.listRouterVO();
        Assert.isTrue(list.size()>0);
    }

}
