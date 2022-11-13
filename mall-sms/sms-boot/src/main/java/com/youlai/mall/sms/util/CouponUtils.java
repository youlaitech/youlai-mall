package com.youlai.mall.sms.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.youlai.common.base.IBaseEnum;
import com.youlai.mall.sms.enums.CouponFaceValueTypeEnum;
import com.youlai.mall.sms.enums.ValidityPeriodTypeEnum;

import java.math.BigDecimal;
import java.util.Date;

public class CouponUtils {


    /**
     * 计算优惠券面值
     *
     * @param faceValueType 面值类型
     * @param faceValue
     * @param discount
     * @return
     */
    public static String getFaceValue(Integer faceValueType, Long faceValue, BigDecimal discount) {
        String faceValueLabel = null;

        if (faceValueType == null) {
            return null;
        }

        CouponFaceValueTypeEnum couponFaceValueTypeEnum = IBaseEnum.getEnumByValue(faceValueType, CouponFaceValueTypeEnum.class);
        switch (couponFaceValueTypeEnum) {
            case CASH:
                faceValueLabel = NumberUtil.toStr(NumberUtil.div(faceValue, new Float(100), 2)) + "元";
                break;
            case DISCOUNT:
                faceValueLabel = NumberUtil.mul(discount, 10) + "折";
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
        if (validityPeriodType == null) {
            return null;
        }
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
