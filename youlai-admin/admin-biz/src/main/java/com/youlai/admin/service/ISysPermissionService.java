package com.youlai.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.entity.SysDictItem;
import com.youlai.admin.pojo.entity.SysPermission;
import com.youlai.admin.pojo.vo.TreeVO;

import java.util.List;

public interface ISysPermissionService extends IService<SysPermission> {

    List<SysPermission> listForPermissionRoles();

    List<TreeVO> listForTree(LambdaQueryWrapper<SysPermission> baseQuery);

    IPage<SysPermission> list(Page<SysPermission> page, SysPermission permission);
}
