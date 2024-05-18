package com.youlai.mall.product.enums;

import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 属性录入方式枚举
 *
 * @author Ray Hao
 * @since 2024/4/19
 */
@Getter
public enum AttributeInputTypeEnum implements IBaseEnum<Integer> {

    MANUAL(1, "手动录入"),
    SELECT(2, "可选列表");

    AttributeInputTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
    // @EnumValue //  Mybatis-Plus 提供注解表示插入数据库时插入该值
    private final Integer value;

    // @JsonValue //  表示对枚举序列化时返回此字段
    private final String label;

}
