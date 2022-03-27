package com.youlai.common.enums;

import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 认证身份标识枚举
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021/10/4
 */
public enum AuthenticationIdentityEnum implements IBaseEnum<String> {

    USERNAME("username", "用户名"),
    MOBILE("mobile", "手机号"),
    OPENID("openId", "开放式认证系统唯一身份标识");

    @Getter
    private String value;

    @Getter
    private String label;

    AuthenticationIdentityEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
