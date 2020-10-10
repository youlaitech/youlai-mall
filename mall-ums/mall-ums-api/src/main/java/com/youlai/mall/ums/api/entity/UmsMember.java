package com.youlai.mall.ums.api.entity;


import lombok.Data;

import java.time.LocalDate;

@Data
public class UmsMember {

    private Long id;

    private String username;

    private String password;

    private Integer gender;

    private String nickname;

    private String mobile;

    private LocalDate birthday;

    private String avatar;

    private String openid;

    private String sessionKey;

    private Integer status;


}
