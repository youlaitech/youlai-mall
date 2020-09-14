package com.youlai.admin.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterVO {

    @ApiModelProperty(example = "/admin")
    private String path;

    @ApiModelProperty(example = "Layout")
    private String component;

    @ApiModelProperty(example = "/admin/user")
    private String redirect;

    @ApiModelProperty(example = "true")
    private boolean alwaysShow;

    @ApiModelProperty(example = "Admin")
    private String name;

    private Meta meta;

    @Data
    @AllArgsConstructor
    public class Meta {
        private String title;
        private String icon;
        private List<Integer> roles;
    }
    private List<RouterVO> children;

}
