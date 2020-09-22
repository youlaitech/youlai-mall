package com.youlai.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.api.entity.SysResource;

import java.util.List;

public interface ISysResourceService extends IService<SysResource> {

    List<SysResource> listByUserId(Integer userId);


    List<SysResource> listForResourceRoles();
}
