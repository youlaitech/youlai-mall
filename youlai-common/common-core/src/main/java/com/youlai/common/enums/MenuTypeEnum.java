package com.youlai.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 菜单类型枚举
 *
 * @author haoxr
 * @date 2022/4/23 9:36
 */

public enum MenuTypeEnum implements IBaseEnum<String> {

    NULL(null, null),
    MENU("M", "菜单"),

    BUTTON("B", "按钮"),
    CATALOG("C", "目录"),
    EXTLINK("E", "外链");

    @Getter
    @EnumValue //  Mybatis-Plus 提供注解表示插入数据库时插入该值
    private String value;

    @Getter
    // @JsonValue //  表示对枚举序列化时返回此字段
    private String label;

    MenuTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

}
