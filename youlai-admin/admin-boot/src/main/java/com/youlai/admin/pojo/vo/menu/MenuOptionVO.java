package com.youlai.admin.pojo.vo.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.youlai.common.web.domain.Option;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MenuOptionVO {

    public MenuOptionVO(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public MenuOptionVO(String value, String label, List<MenuOptionVO> children) {
        this.value = value;
        this.label = label;
        this.children = children;
    }

    @ApiModelProperty("选项的值")
    private String value;

    @ApiModelProperty("选项的标签，若不设置则默认与value相同")
    private String label;


    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @ApiModelProperty("是否禁用该选项，默认false")
    public Boolean disabled;


    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<MenuOptionVO> children;

    private Boolean isPerm;


    private List<MenuOptionVO> perms;
}
