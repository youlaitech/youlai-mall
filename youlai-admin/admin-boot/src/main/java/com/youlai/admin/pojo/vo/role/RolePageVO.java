package com.youlai.admin.pojo.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("角色分页视图对象")
@Data
public class RolePageVO {

    @ApiModelProperty("角色ID")
    private Long id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色编码")
    private String code;
}
