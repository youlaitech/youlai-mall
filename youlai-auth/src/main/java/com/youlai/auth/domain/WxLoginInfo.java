package com.youlai.auth.domain;

import lombok.Data;

@Data
public class WxLoginInfo {

    private String code;

    private UserInfo userInfo;

    @Data
    public class UserInfo {
        private String nickName;
        private String avatarUrl;
        private String country;
        private String province;
        private String city;
        private String language;
        private Integer gender;
    }
}
