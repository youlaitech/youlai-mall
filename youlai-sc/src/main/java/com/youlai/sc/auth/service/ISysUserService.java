package com.youlai.sc.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.sc.auth.entity.SysUser;

public interface ISysUserService extends IService<SysUser> {

    SysUser getByUserName(String username);

}
