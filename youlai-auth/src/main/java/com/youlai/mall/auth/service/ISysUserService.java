package com.youlai.mall.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.auth.entity.SysUser;

public interface ISysUserService extends IService<SysUser> {
    SysUser getByUserName(String username);
}
