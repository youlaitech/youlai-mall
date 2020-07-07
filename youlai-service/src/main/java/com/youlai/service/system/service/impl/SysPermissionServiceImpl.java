package com.youlai.service.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.service.system.entity.SysPermission;
import com.youlai.service.system.mapper.SysPermissionMapper;
import com.youlai.service.system.service.ISysPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Override
    public List<SysPermission> getByUserId(Long userId) {
        return this.baseMapper.selectByUserId(userId);
    }

}
