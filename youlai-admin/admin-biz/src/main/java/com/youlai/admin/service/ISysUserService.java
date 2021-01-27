package com.youlai.admin.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.entity.SysUser;

public interface ISysUserService extends IService<SysUser> {

    IPage<SysUser> list(Page<SysUser> page, SysUser sysUser);

    boolean saveUser(SysUser user);

    boolean updateUser(SysUser user);

}
