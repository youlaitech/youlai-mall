package com.fly.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly.auth.entity.SysUser;

public interface ISysUserService extends IService<SysUser> {
    SysUser getByUserName(String username);
}
