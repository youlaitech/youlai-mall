package com.youlai.admin.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserVO {

    private Long id;

    private String nickname;

    private String avatar;

    private List<String> roles;

    private List<String> perms ;

}
