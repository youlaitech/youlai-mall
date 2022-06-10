package com.youlai.admin.pojo.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户表单持久化对象
 *
 * @author haoxr
 * @date 2022/6/10
 */
@Data
public class UserFormPO {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("性别(1:男;2:女)")
    private Integer gender;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("状态(1:启用;0:禁用)")
    private Integer status;

    @ApiModelProperty("部门ID")
    private Long deptId;

    @ApiModelProperty("角色ID集合")
    private List<Long> roleIds;

}
