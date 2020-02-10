package com.fly.cloud.authentication.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly.cloud.authentication.server.pojo.entity.SysPermission;

import java.util.List;

public interface ISysPermissionService extends IService<SysPermission> {
    List<SysPermission> getByUserId(Long userId);
}
