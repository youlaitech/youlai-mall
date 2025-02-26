package com.youlai.mall.member.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 会员传输层对象
 *
 * @author Ray.Hao
 * @since 2022/2/12
 */
@Data
public class MemberRegisterDTO {

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
