package com.youlai.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.api.entity.SysResource;
import com.youlai.admin.mapper.SysResourceMapper;
import com.youlai.admin.service.ISysResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements ISysResourceService {

    @Override
    public List<SysResource> listByUserId(Integer userId) {
        List<SysResource> list = this.baseMapper.listByUserId(userId);
        return list;
    }

    @Override
    public List<SysResource> listForResourceRoles() {
        return this.baseMapper.listForResourceRoles();
    }
}
