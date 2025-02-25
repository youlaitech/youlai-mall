package com.youlai.codegen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.youlai.common.base.IBaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 查询类型枚举
 *
 * @author Ray.Hao
 * @since 2.10.0
 */
@Getter
@RequiredArgsConstructor
public enum QueryTypeEnum implements IBaseEnum<Integer> {

    /** 等于 */
    EQ(1, "="),

    /** 模糊匹配 */
    LIKE(2, "LIKE '%s%'"),

    /** 包含 */
    IN(3, "IN"),

    /** 范围 */
    BETWEEN(4, "BETWEEN"),

    /** 大于 */
    GT(5, ">"),

    /** 大于等于 */
    GE(6, ">="),

    /** 小于 */
    LT(7, "<"),

    /** 小于等于 */
    LE(8, "<="),

    /** 不等于 */
    NE(9, "!="),

    /** 左模糊匹配 */
    LIKE_LEFT(10, "LIKE '%s'"),

    /** 右模糊匹配 */
    LIKE_RIGHT(11, "LIKE 's%'");


    // 存储在数据库中的枚举属性值
    @EnumValue
    @JsonValue
    private final Integer value;

    // 序列化成 JSON 时的属性值
    private final String label;


    @JsonCreator
    public static QueryTypeEnum fromValue(Integer value) {
        for (QueryTypeEnum type : QueryTypeEnum.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + value);
    }

}
