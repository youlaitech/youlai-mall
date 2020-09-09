package com.youlai.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.domain.entity.SysPermission;
import com.youlai.admin.mapper.SysPermissionMapper;
import com.youlai.admin.service.ISysPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Override
    public List<SysPermission> listByUserId(Integer userId) {
        List<SysPermission> list = this.baseMapper.listByUserId(userId);
        return list;
    }
}
