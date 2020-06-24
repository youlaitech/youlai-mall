package com.youlai.mall.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.auth.entity.SysPermission;
import com.youlai.mall.auth.mapper.SysPermissionMapper;
import com.youlai.mall.auth.service.ISysPermissionService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Override
    public List<SysPermission> getByUserId(Long userId) {
        return this.baseMapper.selectByUserId(userId);
    }
}
