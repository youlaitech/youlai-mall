package com.youlai.mall.pms.common.enums;

import lombok.Getter;
import lombok.Setter;

public enum InventoryOperationEnum {

    PAGE("page" ),  // 分页查询
    LIST("list"), //列表查询
    TREE("tree"),//树形列表
    CASCADER("cascader"), //  级联列表 对应级联选择器的下拉格式数据
    ROUTER("router") ;// 路由列表

    @Getter
    @Setter
    private String code;

    InventoryOperationEnum(String code) {
        this.code=code;
    }

    public static InventoryOperationEnum getValue(String code){
        for (InventoryOperationEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return PAGE;
    }

}
