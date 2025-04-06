package com.youlai.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

/**
 * 当前登录用户对象
 *
 * @author haoxr
 * @since 2022/1/14
 */
@Schema(description ="当前登录用户对象")
@Data
public class CurrentUserDTO {

    @Schema(description="用户ID")
    private Long userId;

    @Schema(description="用户名")
    private String username;

    @Schema(description="用户昵称")
    private String nickname;

    @Schema(description="头像地址")
    private String avatar;

    @Schema(description="用户角色编码集合")
    private Set<String> roles;

    @Schema(description="用户权限标识集合")
    private Set<String> perms;

}
