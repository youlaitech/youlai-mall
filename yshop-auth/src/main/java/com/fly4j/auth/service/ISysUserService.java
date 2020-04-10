package com.fly4j.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.auth.entity.SysUser;

public interface ISysUserService extends IService<SysUser> {
    SysUser getByUserName(String username);
}
