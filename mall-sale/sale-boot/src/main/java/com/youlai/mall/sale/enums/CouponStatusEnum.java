package com.youlai.mall.sale.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 优惠券状态枚举
 */
@Getter
public enum CouponStatusEnum implements IBaseEnum<Integer> {

    DRAFT(0, "草稿"),
    NOT_STARTED(1, "未开始"),
    IN_PROGRESS(2, "进行中"),
    ENDED(3, "已结束"),
    INVALID(4, "已失效");

    @Getter
    @EnumValue // Mybatis-Plus 提供注解表示插入数据库时插入该值
    private Integer value;

    @Getter
    @JsonValue // 表示对枚举序列化时返回此字段
    private String label;

    CouponStatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
} 