package com.youlai.mall.pms.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;


@Data
@Schema(description = "属性表单")
public class PmsCategoryAttributeForm {

    @Schema(description="分类ID")
    @NotNull
    private Long categoryId;


    @Schema(description="属性类型（1：规格；2：属性）")
    @NotNull
    private Integer type;

    @Schema(description="属性集合")
    @NotEmpty
    private List<Attribute> attributes;

    @Data
    public static class Attribute {

        @Schema(description="属性ID")
        private Long id;

        @Schema(description="属性名称")
        @NotBlank
        private String name;
    }

}
