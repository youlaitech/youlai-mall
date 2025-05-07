package com.youlai.mall.sale.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 优惠券使用范围枚举（位运算）
 */
@Getter
public enum CouponUseScopeEnum implements IBaseEnum<Integer> {

    ALL(1, "全场通用"),
    CATEGORY(2, "指定商品分类"),
    SPU(4, "指定商品"),
    CATEGORY_AND_SPU(6, "指定分类和商品")

    ;

    private final Integer value;

    private final String label;

    CouponUseScopeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
    
    /**
     * 判断使用范围是否包含指定类型
     * 
     * @param useScope 使用范围值
     * @param scopeType 指定的范围类型
     * @return 是否包含
     */
    public static boolean hasScope(Integer useScope, CouponUseScopeEnum scopeType) {
        if (useScope == null || scopeType == null) {
            return false;
        }
        return (useScope & scopeType.getValue()) != 0;
    }
} 