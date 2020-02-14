package com.fly.cloud.auth.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.cloud.auth.mapper.SysPermissionMapper;
import com.fly.cloud.auth.service.ISysPermissionService;
import com.fly.common.pojo.entity.SysPermission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Override
    public List<SysPermission> getByUserId(Long userId) {
        return this.baseMapper.selectByUserId(userId);
    }
}
