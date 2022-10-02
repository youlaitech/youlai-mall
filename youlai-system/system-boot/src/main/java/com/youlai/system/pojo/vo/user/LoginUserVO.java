package com.youlai.system.pojo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * 登录用户视图层对象
 *
 * @author haoxr
 * @date 2022/1/14
 */
@ApiModel("登录用户视图层对象")
@Data
public class LoginUserVO {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("头像地址")
    private String avatar;

    @ApiModelProperty("用户角色Code集合")
    private Set<String> roles;

    @ApiModelProperty("用户权限标识集合")
    private Set<String> perms;

}
