package com.youlai.system.model.bo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户持久化对象
 *
 * @author haoxr
 * @since 2022/6/10
 */
@Data
public class UserBO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 账户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 性别(1->男；2->女)
     */
    private Integer gender;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态: 1->启用;0->禁用
     */
    private Integer status;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 角色名称，多个使用英文逗号(,)分割
     */
    private String roleNames;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
