package com.youlai.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.api.entity.SysRole;

import java.util.List;

public interface ISysRoleService extends IService<SysRole> {

    boolean update(SysRole role);

    boolean delete(List<Integer> ids);

    boolean add(SysRole role);

    boolean update(Integer id, List<Integer> resourceIds);
}
