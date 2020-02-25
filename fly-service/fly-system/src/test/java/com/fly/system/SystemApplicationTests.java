package com.fly.system;

import com.fly.system.pojo.entity.SysUser;
import com.fly.system.pojo.entity.TUser;
import com.fly.system.service.ISysUserService;
import com.fly.system.service.ITUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SystemTests {


    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private ITUserService iTUserService;

    /**
     * 双数据源测试
     */
    @Test
    public void testGetUser() {
        SysUser sysUser = iSysUserService.getById(1);
        log.info("mysql数据源：{}",sysUser.toString());
        TUser tUser = iTUserService.getById(1);
        log.info("oracle数据源：{}",tUser.toString());
    }
}
