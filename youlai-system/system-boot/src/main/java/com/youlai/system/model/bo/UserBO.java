package com.youlai.system.model.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户持久化对象
 *
 * @author Ray.Hao
 * @date 2022/6/10
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
     * 状态(1-启用 0-禁用)
     */
    private Integer status;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;


    /**
     * 角色ID集合
     */
    private List<Long> roleIds;

    /**
     * 角色名称，多个使用英文逗号(,)分割
     */
    private String roleNames;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

}
