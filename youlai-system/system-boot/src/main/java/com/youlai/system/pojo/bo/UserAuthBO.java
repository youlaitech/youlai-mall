package com.youlai.system.pojo.bo;


import lombok.Data;

import java.util.List;

/**
 * 用户认证信息业务对象
 *
 * @author haoxr
 * @date 2021/9/27
 */
@Data
public class UserAuthBO {

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

    /**
     * 用户角色数据权限集合
     */
    private List<Integer> dataScopes;

}
