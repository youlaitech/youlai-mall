package com.youlai.mall.sms.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.youlai.common.base.IBaseEnum;
import com.youlai.mall.sms.common.enums.CouponTypeEnum;
import com.youlai.mall.sms.common.enums.ValidityPeriodTypeEnum;

import java.math.BigDecimal;
import java.util.Date;

public class CouponUtils {


    /**
     * 计算优惠券面值
     *
     * @param type
     * @param faceValue
     * @param discount
     * @return
     */
    public static String getFaceValue(Integer type, Long faceValue, BigDecimal discount) {
        String faceValueLabel = null;

        CouponTypeEnum couponTypeEnum = IBaseEnum.getEnumByValue(type, CouponTypeEnum.class);
        switch (couponTypeEnum) {
            case ZK:
                faceValueLabel = NumberUtil.mul(discount, 10) + "折";
                break;
            case MJ:
            case ZJ:
                faceValueLabel = NumberUtil.div(faceValue, new Float(10000), 2) + "元";
                break;
        }
        return faceValueLabel;
    }


    /**
     * 计算优惠券有效期
     *
     * @param validityPeriodType
     * @param validityDays
     * @param validityBeginTime
     * @param validityEndTime
     * @return
     */
    public static String getValidityPeriod(Integer validityPeriodType, Integer validityDays, Date validityBeginTime, Date validityEndTime) {
        String validityPeriodLabel = null;
        ValidityPeriodTypeEnum validityPeriodTypeEnum = IBaseEnum.getEnumByValue(validityPeriodType, ValidityPeriodTypeEnum.class);
        switch (validityPeriodTypeEnum) {
            case DATE_RANGE:
                validityPeriodLabel = DateUtil.format(validityBeginTime, "yyyy/MM/dd") + "~" + DateUtil.format(validityEndTime, "yyyy/MM/dd");
                break;
            case FIXED_DAYS:
                validityPeriodLabel = "领取后" + validityDays + "天有效";
                break;
        }
        return validityPeriodLabel;
    }
}
