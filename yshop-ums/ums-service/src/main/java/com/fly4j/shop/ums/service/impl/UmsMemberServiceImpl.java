package com.fly4j.shop.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.shop.ums.mapper.UmsMemberMapper;
import com.fly4j.shop.ums.service.IUmsMemberService;
import com.fly4j.yshop.ums.pojo.entity.UmsMember;
import org.springframework.stereotype.Service;

@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements IUmsMemberService {

}
