package com.youlai.mall.sale.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.youlai.common.base.IBaseEnum;
import com.youlai.mall.sale.enums.CouponFaceValueTypeEnum;
import com.youlai.mall.sale.enums.ValidityPeriodTypeEnum;
import com.youlai.mall.sale.enums.CouponStatusEnum;
import com.youlai.mall.sale.enums.CouponTypeEnum;
import com.youlai.mall.sale.enums.CouponUseScopeEnum;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券工具类
 */
public class CouponUtils {

    /**
     * 获取优惠券状态文本
     *
     * @param status 状态码
     * @return 状态文本
     */
    public static String getStatusLabel(Integer status) {
        return IBaseEnum.getLabelByValue(status, CouponStatusEnum.class);
    }

    /**
     * 获取优惠券类型文本
     *
     * @param type 类型码
     * @return 类型文本
     */
    public static String getTypeLabel(Integer type) {
        return IBaseEnum.getLabelByValue(type, CouponTypeEnum.class);
    }

    /**
     * 获取优惠券使用范围文本
     *
     * @param useScope 使用范围值
     * @return 使用范围文本
     */
    public static String getUseScopeLabel(Integer useScope) {
        if (useScope == null) {
            return "";
        }
        
        // 如果是CATEGORY_AND_SPU的枚举值(6)，直接返回对应的标签
        if (useScope.equals(CouponUseScopeEnum.CATEGORY_AND_SPU.getValue())) {
            return CouponUseScopeEnum.CATEGORY_AND_SPU.getLabel();
        }
        
        // 其他情况按位运算处理
        StringBuilder result = new StringBuilder();
        
        if (CouponUseScopeEnum.hasScope(useScope, CouponUseScopeEnum.ALL)) {
            result.append(CouponUseScopeEnum.ALL.getLabel());
        } else {
            if (CouponUseScopeEnum.hasScope(useScope, CouponUseScopeEnum.CATEGORY)) {
                result.append(CouponUseScopeEnum.CATEGORY.getLabel());
            }
            
            if (CouponUseScopeEnum.hasScope(useScope, CouponUseScopeEnum.SPU)) {
                if (result.length() > 0) {
                    result.append("、");
                }
                result.append(CouponUseScopeEnum.SPU.getLabel());
            }
        }
        
        return result.toString();
    }

    /**
     * 计算优惠券的优惠金额或折扣
     *
     * @param type 优惠券类型
     * @param discountAmount 优惠金额/折扣值
     * @param discountLimit 最高折扣限额
     * @return 格式化后的优惠券面值
     */
    public static String getDiscountValueLabel(Integer type, BigDecimal discountAmount, BigDecimal discountLimit) {
        if (type == null || discountAmount == null) {
            return "";
        }
        
        CouponTypeEnum couponType = IBaseEnum.getEnumByValue(type, CouponTypeEnum.class);
        if (couponType == null) {
            return "";
        }
        
        switch (couponType) {
            case MJ: // 满减券
            case ZJ: // 直减券
                return "¥" + NumberUtil.decimalFormat("#.##", discountAmount);
            case ZK: // 折扣券
                String discountText = discountAmount.multiply(new BigDecimal("10")) + "折";
                if (discountLimit != null && discountLimit.compareTo(BigDecimal.ZERO) > 0) {
                    discountText += "，最高减" + NumberUtil.decimalFormat("#.##", discountLimit) + "元";
                }
                return discountText;
            case BY: // 包邮券
                return "包邮";
            default:
                return "";
        }
    }
    
    /**
     * 获取优惠券有效期描述
     *
     * @param validType 有效期类型
     * @param validDays 有效天数
     * @param validStartTime 有效期开始时间
     * @param validEndTime 有效期结束时间
     * @return 有效期描述
     */
    public static String getValidityPeriodLabel(Integer validType, Integer validDays, Date validStartTime, Date validEndTime) {
        if (validType == null) {
            return "";
        }
        
        // 1-固定日期范围; 2-领取后天数
        if (validType == 1) {
            if (validStartTime != null && validEndTime != null) {
                return DateUtil.format(validStartTime, "yyyy.MM.dd") + " - " + DateUtil.format(validEndTime, "yyyy.MM.dd");
            }
        } else if (validType == 2) {
            if (validDays != null) {
                return "领取后" + validDays + "天内有效";
            }
        }
        
        return "";
    }
    
    /**
     * 获取使用门槛描述
     *
     * @param minAmount 最低使用金额
     * @return 使用门槛描述
     */
    public static String getMinAmountLabel(BigDecimal minAmount) {
        if (minAmount == null || minAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return "无门槛";
        }
        return "满" + NumberUtil.decimalFormat("#.##", minAmount) + "元可用";
    }
    
    /**
     * 获取是否可叠加使用文本
     */
    public static String getSuperimposeLabel(Integer canSuperimpose) {
        return canSuperimpose != null && canSuperimpose == 1 ? "是" : "否";
    }
    
    /**
     * 获取是否仅首单可用文本
     */
    public static String getFirstOrderOnlyLabel(Integer firstOrderOnly) {
        return firstOrderOnly != null && firstOrderOnly == 1 ? "是" : "否";
    }

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
}
