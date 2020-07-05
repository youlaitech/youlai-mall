package com.youlai.service.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.service.oauth.entity.SysUser;

public interface ISysUserService extends IService<SysUser> {

    SysUser getByUserName(String username);

}
