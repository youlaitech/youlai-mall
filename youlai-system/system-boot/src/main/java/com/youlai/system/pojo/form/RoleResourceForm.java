package com.youlai.system.pojo.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@ApiModel("菜单资源表单")
@Data
public class RoleResourceForm {

    @ApiModelProperty("菜单ID集合")
    private List<Long> menuIds;

    @ApiModelProperty("权限ID集合")
    private List<Long> permIds;

}
