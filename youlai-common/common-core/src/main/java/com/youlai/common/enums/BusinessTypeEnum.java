package com.youlai.common.enums;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author haoxr
 * @date 2021-02-17 13:13
 *
 */
public enum BusinessTypeEnum {

    USER("100", "用户类型编号"),
    MEMBER("200", "会员类型编号"),
    ORDER("300", "订单类型编号");

    @Getter
    @Setter
    private String code;

    BusinessTypeEnum(String code, String desc) {
        this.code = code;
    }

    public static BusinessTypeEnum getValue(String code){
        for (BusinessTypeEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
