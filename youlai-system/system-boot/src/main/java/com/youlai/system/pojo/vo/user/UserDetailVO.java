package com.youlai.system.pojo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户表详情视图对象
 *
 * @author haoxr
 * @date 2022/8/25
 */
@ApiModel
@Data
public class UserDetailVO {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("")
    private String mobile;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("用户状态(1:正常;0:禁用)")
    private Integer status;

    @ApiModelProperty("部门ID")
    private Long deptId;

    @ApiModelProperty("角色ID集合")
    private List<Long> roleIds;

}
