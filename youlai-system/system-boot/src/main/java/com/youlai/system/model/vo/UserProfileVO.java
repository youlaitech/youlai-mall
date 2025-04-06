package com.youlai.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 个人中心用户信息
 *
 * @author Ray
 * @since 2024/8/13
 */
@Schema(description = "个人中心用户信息")
@Data
public class UserProfileVO {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "头像URL")
    private String avatar;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "部门名称")
    private String deptName;

    @Schema(description = "角色名称")
    private String roleNames;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

}
