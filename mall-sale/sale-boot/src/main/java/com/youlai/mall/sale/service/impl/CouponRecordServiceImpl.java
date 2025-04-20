package com.youlai.mall.sale.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.sale.mapper.CouponRecordMapper;
import com.youlai.mall.sale.model.entity.CouponRecord;
import com.youlai.mall.sale.service.CouponRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 优惠券领取记录服务实现类
 */
@Service
@RequiredArgsConstructor
public class CouponRecordServiceImpl extends ServiceImpl<CouponRecordMapper, CouponRecord> implements CouponRecordService {

} 