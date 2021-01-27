package com.youlai.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.entity.SysPermission;
import com.youlai.admin.pojo.vo.TreeVO;

import java.util.List;

public interface ISysPermissionService extends IService<SysPermission> {

    List<SysPermission> listForPermissionRoles();

    List<TreeVO> listForTree(LambdaQueryWrapper<SysPermission> baseQuery);
}
