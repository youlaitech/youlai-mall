package com.youlai.auth.domain;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

@ApiModel
@Data
@Builder

public class Oauth2Token {

    @ApiModelProperty("访问令牌")
    private String token;

    @ApiModelProperty("刷新令牌")
    private String refreshToken;

    @ApiModelProperty("有效时间(秒)")
    private int expiresIn;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private WxMaUserInfo userInfo;

}
