package com.youlai.admin.pojo.vo.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@ApiModel("资源视图对象")
@Data
public class ResourceVO {

    @ApiModelProperty("菜单列表")
    private List<MenuOption> menus;

    @ApiModelProperty
    private List<PermOption> perms;

    @Data
    public static class MenuOption {

        @ApiModelProperty("选项的值")
        private Long value;

        @ApiModelProperty("选项的标签")
        private String label;

        @JsonInclude(value = JsonInclude.Include.NON_NULL)
        @ApiModelProperty("是否禁用该选项，默认false")
        public Boolean disabled;

        @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
        private List<MenuOption> children;

        @JsonInclude(value = JsonInclude.Include.NON_NULL)
        private Boolean isPerm;

        @JsonInclude(value = JsonInclude.Include.NON_NULL)
        private Long permPid;

    }


    @Data
    @AllArgsConstructor
    public static class PermOption {

        @ApiModelProperty("父ID")
        private Long parentId;

        @ApiModelProperty("选项的值")
        private Long value;

        @ApiModelProperty("选项的标签")
        private String label;

    }


}
