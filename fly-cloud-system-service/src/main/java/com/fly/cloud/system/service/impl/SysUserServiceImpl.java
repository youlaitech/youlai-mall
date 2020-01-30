package com.fly.cloud.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.cloud.system.service.ISysUserService;
import com.fly.cloud.system.mapper.SysUserMapper;
import com.fly.cloud.system.pojo.entity.SysUser;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
