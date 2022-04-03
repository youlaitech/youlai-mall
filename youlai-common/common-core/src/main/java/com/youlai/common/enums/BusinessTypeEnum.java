package com.youlai.common.enums;

import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 业务类型枚举
 *
 * @author haoxr
 * @date 2021-02-17
 */
public enum BusinessTypeEnum implements IBaseEnum<Integer> {

    USER(100, "用户"),
    MEMBER(200, "会员"),
    ORDER(300, "订单");

    @Getter
    private Integer value;

    @Getter
    private String label;

    BusinessTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
