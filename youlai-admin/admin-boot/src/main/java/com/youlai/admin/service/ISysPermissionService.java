package com.youlai.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.entity.SysPermission;

import java.util.List;

public interface ISysPermissionService extends IService<SysPermission> {

    List<SysPermission> listPermRoles();

    IPage<SysPermission> list(Page<SysPermission> page, SysPermission permission);

    boolean refreshPermRolesRules();

    List<String> listBtnPermByRoles(List<String> roles);
}
