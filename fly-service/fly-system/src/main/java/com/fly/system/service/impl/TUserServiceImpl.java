package com.fly.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.system.mapper.oracle.TUserMapper;
import com.fly.system.pojo.entity.TUser;
import com.fly.system.service.ITUserService;
import org.springframework.stereotype.Service;

@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

}
