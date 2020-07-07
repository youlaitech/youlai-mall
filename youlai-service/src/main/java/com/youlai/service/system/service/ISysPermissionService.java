package com.youlai.service.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.service.system.entity.SysPermission;

import java.util.List;

public interface ISysPermissionService extends IService<SysPermission> {

    List<SysPermission> getByUserId(Long userId);

}
