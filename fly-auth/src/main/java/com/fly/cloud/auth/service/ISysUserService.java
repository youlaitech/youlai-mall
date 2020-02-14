package com.fly.cloud.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly.common.pojo.entity.SysUser;

public interface ISysUserService extends IService<SysUser> {
    SysUser getByUserName(String username);
}
