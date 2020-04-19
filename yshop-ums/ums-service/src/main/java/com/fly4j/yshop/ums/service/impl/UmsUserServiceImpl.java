package com.fly4j.yshop.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.yshop.ums.mapper.UmsUserMapper;
import com.fly4j.yshop.ums.service.IUmsUserService;
import com.fly4j.yshop.ums.pojo.entity.UmsUser;
import org.springframework.stereotype.Service;

@Service
public class UmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsUser> implements IUmsUserService {

}
