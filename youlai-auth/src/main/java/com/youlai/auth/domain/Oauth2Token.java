package com.youlai.auth.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Oauth2Token {

    @ApiModelProperty("访问令牌")
    private String token ;

    @ApiModelProperty("刷新令牌")
    private String refreshToken;

    @ApiModelProperty("访问令牌头前缀")
    private String tokenHead;

    @ApiModelProperty("有效时间(秒)")
    private int expiresIn;

}
