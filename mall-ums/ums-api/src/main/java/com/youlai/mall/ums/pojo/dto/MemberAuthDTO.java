package com.youlai.mall.ums.pojo.dto;

import lombok.Data;

@Data
public class MemberAuthDTO {

    private Long id;
    private String username;
    private Integer status;
    private String avatar;
    private String nickname;

}
