package com.youlai.mall.ums.api.dto;

import lombok.Data;

@Data
public class MemberDTO {

    private Integer id;
    private String username;
    private String password;
    private Integer status;
    private String clientId;

    private String avatar;
    private String nickname;

}
