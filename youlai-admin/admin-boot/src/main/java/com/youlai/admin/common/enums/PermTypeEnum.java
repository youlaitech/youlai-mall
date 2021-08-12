package com.youlai.admin.common.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * 权限类型
 *
 * @author xianrui
 * @date 2021-02-05
 */

public enum PermTypeEnum {

    ROUTE(1, "接口权限"),
    BUTTON(2, "按钮权限");

    @Getter
    @Setter
    private Integer value;

    PermTypeEnum(Integer value, String desc) {
        this.value = value;
    }
}
