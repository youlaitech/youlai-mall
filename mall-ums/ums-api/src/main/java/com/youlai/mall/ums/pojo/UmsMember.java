package com.youlai.mall.ums.pojo;


import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
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

    private Integer integration;


}
