package com.youlai.admin.dto;

import lombok.Data;

import java.util.List;

/**
 * OAuth2认证用户信息传输层对象
 *
 * @author haoxr
 * @date 2021/9/27
 */
@Data
public class AuthUserDTO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户状态(1:正常;0:禁用)
     */
    private Integer status;

    /**
     * 用户角色编码集合 ["ROOT","ADMIN"]
     */
    private List<String> roles;

    /**
     * 部门ID
     */
    private Long deptId;

}
