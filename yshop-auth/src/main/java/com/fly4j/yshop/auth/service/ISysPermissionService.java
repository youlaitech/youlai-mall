package com.fly4j.yshop.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.yshop.auth.entity.SysPermission;

import java.util.List;

public interface ISysPermissionService extends IService<SysPermission> {
    List<SysPermission> getByUserId(Long userId);
}
