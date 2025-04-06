package com.youlai.system.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 字典项分页对象
 *
 * @author Ray.Hao
 * @since 0.0.1
 */
@Schema(description = "字典项分页对象")
@Getter
@Setter
public class DictItemPageVO {

    @Schema(description = "字典项ID")
    private Long id;

    @Schema(description = "字典编码")
    private String dictCode;

    @Schema(description = "字典标签")
    private String label;

    @Schema(description = "字典值")
    private String value;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态（1:启用，0:禁用）")
    private Integer status;

}
