package com.fly.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly.system.pojo.entity.SysUser;

public interface ISysUserService extends IService<SysUser> {
    SysUser getByUserName(String username);
}
