package com.youlai.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.youlai.boot.common.base.IBaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 表单类型枚举
 *
 * @author Ray
 * @since 2.10.0
 */
@Getter
@RequiredArgsConstructor
public enum FormTypeEnum implements IBaseEnum<Integer> {

    /**
     * 输入框
     */
    INPUT(1, "输入框"),

    /**
     * 下拉框
     */
    SELECT(2, "下拉框"),

    /**
     * 单选框
     */
    RADIO(3, "单选框"),

    /**
     * 复选框
     */
    CHECK_BOX(4, "复选框"),

    /**
     * 数字输入框
     */
    INPUT_NUMBER(5, "数字输入框"),

    /**
     * 开关
     */
    SWITCH(6, "开关"),

    /**
     * 文本域
     */
    TEXT_AREA(7, "文本域"),

    /**
     * 日期时间框
     */
    DATE(8, "日期框"),

    /**
     * 日期框
     */
    DATE_TIME(9, "日期时间框");


    //  Mybatis-Plus 提供注解表示插入数据库时插入该值
    @EnumValue
    @JsonValue
    private final Integer value;

    // @JsonValue //  表示对枚举序列化时返回此字段
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
