package com.youlai.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.Data;

/**
 * 用户表
 */
@Data
public class SysUser extends BaseEntity {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别((1:男;2:女))
     */
    private Integer gender;

    /**
     * 密码
     */
    private String password;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 联系方式
     */
    private String mobile;

    /**
     * 用户状态(1:正常;0:禁用)
     */
    private Integer status;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 逻辑删除标识(0:未删除;1:已删除)
     */
    private Integer deleted;

}