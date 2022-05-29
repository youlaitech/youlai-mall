package com.youlai.mall.sms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.sms.pojo.entity.SmsCoupon;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.sms.pojo.query.CouponPageQuery;
import com.youlai.mall.sms.pojo.vo.CouponPageVO;

/**
 *
 */
public interface SmsCouponService extends IService<SmsCoupon> {

    /**
     * 优惠券分页列表
     *
     * @param queryParams
     * @return
     */
    Page<CouponPageVO> listCouponsPage(CouponPageQuery queryParams);
}
