package com.youlai.system.model.form;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典数据表单对象
 *
 * @author Ray.Hao
 * @since 2.9.0
 */
@Schema(description = "字典数据表单")
@Data
public class DictDataForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "字典ID")
    private Long id;

    @Schema(description = "字典编码")
    private String dictCode;

    @Schema(description = "字典值")
    private String value;

    @Schema(description = "字典标签")
    private String label;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态（1-启用，0-禁用）")
    private Integer status;

    @Schema(description = "标签类型")
    private String tagType;

}
