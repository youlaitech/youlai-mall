package com.youlai.auth.security.extension.wechat;

import lombok.Data;

/**
 * 微信用户信息
 *
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 * @date 2021/9/29
 */
@Data
public class WechatUserInfo {

    private String avatarUrl;

    private String city;

    private String country;

    private Integer gender;

    private String language;

    private String nickName;

    private String province;

}
