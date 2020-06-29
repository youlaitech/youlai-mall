package com.youlai.sms.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.sms.auth.entity.SysPermission;

import java.util.List;

public interface ISysPermissionService extends IService<SysPermission> {

    List<SysPermission> getByUserId(Long userId);

}
