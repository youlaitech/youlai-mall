package com.youlai.admin.service.impl;

import com.youlai.admin.pojo.entity.SysUser;
import com.youlai.admin.service.ISysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021/8/28
 */
@SpringBootTest
class SysUserServiceImplTest {

    @Autowired
    private ISysUserService iSysUserService;

    @Test
    public void saveUser() {
        SysUser user=new SysUser();
        user.setUsername("root");
        user.setNickname("有来技术");
        user.setMobile("17621590365");
        user.setEmail("youlaitech@163.com");
        iSysUserService.saveUser(user);
    }
}