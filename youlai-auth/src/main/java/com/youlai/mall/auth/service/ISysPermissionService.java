package com.youlai.mall.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.auth.entity.SysPermission;

import java.util.List;

public interface ISysPermissionService extends IService<SysPermission> {
    List<SysPermission> getByUserId(Long userId);
}
