package com.youlai.common.core.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统类型枚举
 */
public enum SystemTypeEnum {
    ADMIN(1, "管理系统"),
    WEAPP(2, "微信小程序"),
    ;

    @Getter
    @Setter
    private Integer code;

    SystemTypeEnum(int code, String desc) {
        this.code = code;
    }

    public static SystemTypeEnum getValue(Integer code) {
        for (SystemTypeEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
