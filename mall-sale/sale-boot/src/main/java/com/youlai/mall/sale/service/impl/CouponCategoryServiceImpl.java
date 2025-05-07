package com.youlai.mall.sale.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.sale.mapper.CouponCategoryMapper;
import com.youlai.mall.sale.model.entity.CouponCategory;
import com.youlai.mall.sale.service.CouponCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 优惠券分类关联服务实现类
 */
@Service
@RequiredArgsConstructor
public class CouponCategoryServiceImpl extends ServiceImpl<CouponCategoryMapper, CouponCategory> implements CouponCategoryService {

} 