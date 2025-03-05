package com.youlai.mall.product.model.form;

import java.io.Serial;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 规格表单对象
 *
 * @author Ray.Hao
 * @since 2024-06-13
 */
@Getter
@Setter
@Schema(description = "表单对象")
public class SpecForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "规格名称")
    private String name;

    @Schema(description = "分类ID", example = "1")
    private Long categoryId;

    @Schema(description = "输入类型(1:文本 2:单选 3:多选)")
    private Integer inputType;

    @Schema(description = "可选项(逗号分隔)")
    private String options;

    @Schema(description = "排序", example = "1")
    private Short sort;

}
