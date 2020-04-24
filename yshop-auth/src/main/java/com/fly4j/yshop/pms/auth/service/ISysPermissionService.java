package com.fly4j.yshop.pms.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.yshop.pms.auth.entity.SysPermission;

import java.util.List;

public interface ISysPermissionService extends IService<SysPermission> {
    List<SysPermission> getByUserId(Long userId);
}
