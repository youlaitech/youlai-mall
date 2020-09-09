package com.youlai.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.domain.entity.SysUser;
import com.youlai.admin.mapper.SysUserMapper;
import com.youlai.admin.service.ISysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
