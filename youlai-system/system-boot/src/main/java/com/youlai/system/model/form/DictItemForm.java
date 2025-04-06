package com.youlai.system.model.form;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字典项表单对象
 *
 * @author Ray Hao
 * @since 2.9.0
 */
@Schema(description = "字典项表单")
@Data
public class DictItemForm {

    @Schema(description = "字典项ID")
    private Long id;

    @Schema(description = "字典编码")
    private String dictCode;

    @Schema(description = "字典项值")
    private String value;

    @Schema(description = "字典项标签")
    private String label;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态（0：禁用，1：启用）")
    private Integer status;

    @Schema(description = "字典类型（用于显示样式）")
    private String tagType;

}
