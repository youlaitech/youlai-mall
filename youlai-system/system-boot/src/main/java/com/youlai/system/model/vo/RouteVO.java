package com.youlai.system.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 菜单路由视图对象
 *
 * @author haoxr
 * @since 2020/11/28
 */
@Schema(description = "路由对象")
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouteVO {

    @Schema(description = "路由路径", example = "user")
    private String path;

    @Schema(description = "组件路径", example = "system/user/index")
    private String component;

    @Schema(description = "跳转链接", example = "https://www.youlai.tech")
    private String redirect;

    @Schema(description = "路由名称")
    private String name;

    @Schema(description = "路由属性")
    private Meta meta;

    @Schema(description = "路由属性类型")
    @Data
    public static class Meta {

        @Schema(description = "路由title")
        private String title;

        @Schema(description = "ICON")
        private String icon;

        @Schema(description = "是否隐藏(true-是 false-否)", example = "true")
        private Boolean hidden;

        @Schema(description = "拥有路由权限的角色编码", example = "['ADMIN','ROOT']")
        private List<String> roles;

        @Schema(description = "【菜单】是否开启页面缓存", example = "true")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Boolean keepAlive;

        @Schema(description = "【目录】只有一个子路由是否始终显示", example = "true")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Boolean alwaysShow;

        @Schema(description = "路由参数")
        private Map<String,String> params;
    }

    @Schema(description = "子路由列表")
    private List<RouteVO> children;
}
