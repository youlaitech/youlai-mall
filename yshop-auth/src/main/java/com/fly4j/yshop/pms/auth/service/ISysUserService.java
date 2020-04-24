package com.fly4j.yshop.pms.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.yshop.pms.auth.entity.SysUser;

public interface ISysUserService extends IService<SysUser> {
    SysUser getByUserName(String username);
}
