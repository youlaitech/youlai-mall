package com.fly4j.yshop.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.yshop.sms.mapper.SmsCouponMapper;
import com.fly4j.yshop.sms.pojo.entity.SmsCoupon;
import com.fly4j.yshop.sms.service.ISmsCouponService;
import org.springframework.stereotype.Service;

@Service
public class ISmsCouponServiceImpl extends ServiceImpl<SmsCouponMapper, SmsCoupon> implements ISmsCouponService {

}