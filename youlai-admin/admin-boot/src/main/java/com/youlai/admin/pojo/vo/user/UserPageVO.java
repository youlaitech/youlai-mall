package com.youlai.admin.pojo.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户分页视图对象
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/1/15 9:41
 */
@ApiModel("用户分页视图对象")
@Data
public class UserPageVO {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("用户头像地址")
    private String avatar;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("用户状态(1:启用;0:禁用)")
    private Integer status;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("角色名称，多个使用英文逗号(,)分割")
    private String roleNames;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date gmtCreate;

}
