package com.youlai.sms.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.sms.auth.entity.SysUser;

public interface ISysUserService extends IService<SysUser> {

    SysUser getByUserName(String username);

}
