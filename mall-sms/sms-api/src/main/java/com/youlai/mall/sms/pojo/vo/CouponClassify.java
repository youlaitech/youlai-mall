package com.youlai.mall.sms.pojo.vo;

import cn.hutool.core.date.DateUtil;
import com.youlai.mall.sms.pojo.domain.SmsCoupon;
import com.youlai.mall.sms.pojo.domain.SmsCouponTemplate;
import com.youlai.mall.sms.pojo.enums.CouponStateEnum;
import com.youlai.mall.sms.pojo.enums.PeriodTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xinyi
 * @desc: 优惠券状态分类
 * @date 2021/7/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponClassify {

    /**
     * 可用优惠券
     */
    private List<SmsCoupon> usable;

    /**
     * 已使用优惠券
     */
    private List<SmsCoupon> used;

    /**
     * 已过期优惠券
     */
    private List<SmsCoupon> expired;

    public static CouponClassify classify(List<SmsCoupon> coupons) {
        List<SmsCoupon> usable = new ArrayList<>(coupons.size());
        List<SmsCoupon> used = new ArrayList<>(coupons.size());
        List<SmsCoupon> expired = new ArrayList<>(coupons.size());
        Date time = new Date();

        coupons.forEach(coupon -> {
            boolean isTimeExpire = false;
            SmsCouponTemplate template = coupon.getTemplate();
            // 判断优惠券是否过期
            if (template.getRule().getExpiration().getPeriod().equals(PeriodTypeEnum.REGULAR.getCode())) {
//                isTimeExpire = template.getRule().getExpiration().getDeadline() <= time.getTime();
            } else {
                isTimeExpire = DateUtil.offsetDay(coupon.getGmtCreate(), template.getRule().getExpiration().getGap()).getTime() <= time.getTime();
            }

            if (coupon.getState().equals(CouponStateEnum.USED)) {
                used.add(coupon);
            } else if (coupon.getState().equals(CouponStateEnum.EXPIRED) || isTimeExpire) {
                coupon.setState(CouponStateEnum.EXPIRED);
                expired.add(coupon);
            } else {
                usable.add(coupon);
            }

        });
        return new CouponClassify(usable, used, expired);
    }
}
