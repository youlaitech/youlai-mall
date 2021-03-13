package com.youlai.common.enums;

import lombok.Getter;
import lombok.Setter;

public enum QueryModeEnum {

    PAGE("page" ),  // 分页查询
    LIST("list"), //列表查询
    TREE("tree"),//树形列表
    CASCADER("cascader"), //  级联列表 对应级联选择器的下拉格式数据
    ROUTER("router") ;// 路由列表

    @Getter
    @Setter
    private String code;

    QueryModeEnum(String code) {
        this.code=code;
    }

    public static QueryModeEnum getValue(String code){
        for (QueryModeEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return PAGE; // 默认分页查询
    }

}
