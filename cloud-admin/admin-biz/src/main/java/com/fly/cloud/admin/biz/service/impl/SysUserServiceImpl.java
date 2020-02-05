package com.fly.cloud.admin.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.cloud.admin.biz.mapper.SysUserMapper;
import com.fly.cloud.admin.biz.service.ISysUserService;
import com.fly.common.pojo.entity.SysUser;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
