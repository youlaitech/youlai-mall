package com.youlai.service.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.service.oauth.entity.SysPermission;

import java.util.List;

public interface ISysPermissionService extends IService<SysPermission> {

    List<SysPermission> getByUserId(Long userId);

}
