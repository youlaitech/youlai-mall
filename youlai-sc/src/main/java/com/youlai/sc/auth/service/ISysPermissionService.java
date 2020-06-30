package com.youlai.sc.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.sc.auth.entity.SysPermission;

import java.util.List;

public interface ISysPermissionService extends IService<SysPermission> {

    List<SysPermission> getByUserId(Long userId);

}
