package com.fly.cloud.authentication.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.cloud.authentication.server.pojo.entity.SysPermission;
import com.fly.cloud.authentication.server.service.ISysPermissionService;
import com.fly.cloud.authentication.server.dao.SysPermissionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Override
    public List<SysPermission> getByUserId(Long userId) {
        return this.baseMapper.selectByUserId(userId);
    }
}
