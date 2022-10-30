package com.youlai.system.pojo.vo.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.youlai.common.web.model.Option;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("资源(菜单+权限)视图对象")
@Data
public class ResourceVO {

    @ApiModelProperty("选项的值")
    private Long value;

    @ApiModelProperty("选项的标签")
    private String label;

    @ApiModelProperty("子菜单")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<ResourceVO> children;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<Option> perms;


}
