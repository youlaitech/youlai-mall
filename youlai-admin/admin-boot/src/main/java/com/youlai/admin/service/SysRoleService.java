package com.youlai.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.entity.SysRole;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    boolean delete(List<Long> ids);
}
