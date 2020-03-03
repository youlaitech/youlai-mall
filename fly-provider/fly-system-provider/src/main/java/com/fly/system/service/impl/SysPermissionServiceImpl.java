package com.fly.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.system.mapper.mysql.SysPermissionMapper;
import com.fly.system.entity.SysPermission;
import com.fly.system.service.ISysPermissionService;
import org.springframework.stereotype.Service;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

}
