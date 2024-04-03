package com.youlai.mall.ums.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 会员传输层对象
 *
 * @author haoxr
 * @since 2022/2/12
 */
@Data
public class MemberRegisterDto {

    private Integer gender;

    private String nickName;

    private String mobile;

    private LocalDate birthday;

    private String avatarUrl;

    private String openid;

    private String sessionKey;

    private String city;

    private String country;

    private String language;

    private String province;

}
