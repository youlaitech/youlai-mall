package com.youlai.system.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description ="菜单视图对象")
@Data
public class MenuVO {

    @Schema(description = "菜单ID")
    private Long id;

    @Schema(description = "父菜单ID")
    private Long parentId;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description="菜单类型")
    private Integer type;

    @Schema(description = "路由名称")
    private String routeName;

    @Schema(description = "路由路径")
    private String routePath;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "菜单排序(数字越小排名越靠前)")
    private Integer sort;

    @Schema(description = "菜单是否可见(1:显示;0:隐藏)")
    private Integer visible;

    @Schema(description = "ICON")
    private String icon;

    @Schema(description = "跳转路径")
    private String redirect;

    @Schema(description="按钮权限标识")
    private String perm;

    @Schema(description = "子菜单")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<MenuVO> children;

}
