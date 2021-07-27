package com.youlai.mall.pms.pojo.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("属性表单")
public class AttributeFormDTO {

    @ApiModelProperty("分类ID")
    @NotNull
    private Long categoryId;


    @ApiModelProperty("属性类型（1：规格；2：属性）")
    @NotNull
    private Integer type;

    @ApiModelProperty("属性集合")
    @NotEmpty
    private List<Attribute> attributes;

    @Data
    public static class Attribute {

        @ApiModelProperty("属性ID")
        private Long id;

        @ApiModelProperty("属性名称")
        @NotBlank
        private String name;
    }

}
