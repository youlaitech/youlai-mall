package com.youlai.mall.ums.pojo.dto;

import lombok.Data;

@Data
public class AuthMemberDTO {

    private Long id;
    private String username;
    private String password;
    private Integer status;

    private String avatar;
    private String nickname;

}
