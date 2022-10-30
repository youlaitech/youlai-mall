package com.youlai.system.pojo.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel("角色表单对象")
@Data
public class RoleForm {

    @ApiModelProperty("角色ID")
    private Long id;

    @ApiModelProperty("角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty("角色编码")
    @NotBlank(message = "角色编码不能为空")
    private String code;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("角色状态(1-正常；0-停用)")
    private Integer status;

}
