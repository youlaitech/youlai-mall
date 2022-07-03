package com.youlai.mall.sms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.sms.pojo.entity.SmsCoupon;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.sms.pojo.form.CouponForm;
import com.youlai.mall.sms.pojo.query.CouponPageQuery;
import com.youlai.mall.sms.pojo.vo.CouponPageVO;

/**
 * 优惠券业务接口
 *
 * @author haoxr
 * @date  2022/5/29
 */
public interface SmsCouponService extends IService<SmsCoupon> {

    /**
     * 优惠券分页列表
     *
     * @param queryParams
     * @return
     */
    Page<CouponPageVO> listCouponPages(CouponPageQuery queryParams);

    /**
     * 新增优惠券
     *
     * @param couponForm
     * @return
     */
    boolean saveCoupon(CouponForm couponForm);

    /**
     * 修改优惠券
     *
     * @param couponId 优惠券ID
     * @param couponForm 优惠券表单
     * @return
     */
    boolean updateCoupon(Long couponId, CouponForm couponForm);

    /**
     * 删除优惠券
     *
     * @param ids 优惠券ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteCoupons(String ids);

    /**
     * 优惠券表单数据
     * 
     * @param couponId
     * @return
     */
    CouponForm getCouponFormData(Long couponId);
}
