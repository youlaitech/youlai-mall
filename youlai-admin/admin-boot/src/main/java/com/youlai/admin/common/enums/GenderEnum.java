package com.youlai.admin.common.enums;

import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 性别枚举
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/4/10 22:34
 */
public enum GenderEnum implements IBaseEnum<Integer> {

    MALE(1, "男"),
    FEMALE(2, "女"),
    UNKNOWN(0, "未知");

    @Getter
    // @EnumValue //  Mybatis-Plus 提供注解表示插入数据库时插入该值
    private Integer value;

    @Getter
    // @JsonValue //  表示对枚举序列化时返回此字段
    private String label;

    GenderEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
