package com.fly4j.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.system.mapper.mysql.SysUserMapper;
import com.fly4j.system.pojo.entity.SysUser;
import com.fly4j.system.service.ISysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
