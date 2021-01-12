package com.youlai.mall.ums.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.ums.pojo.UmsUser;

public interface IUmsUserService extends IService<UmsUser> {

    IPage<UmsUser> list(Page<UmsUser> page, UmsUser user);
}
