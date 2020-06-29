package com.youlai.sms.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.sms.auth.entity.SysUser;
import com.youlai.sms.auth.mapper.SysUserMapper;
import com.youlai.sms.auth.service.ISysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser getByUserName(String username) {
        SysUser sysUser = this.baseMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, username));
        return sysUser;
    }

}
