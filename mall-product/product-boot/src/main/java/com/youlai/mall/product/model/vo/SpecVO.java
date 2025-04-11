package com.youlai.mall.product.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 规格视图对象
 *
 * @author Ray.Hao
 * @since 2024-06-13
 */
@Getter
@Setter
@Schema(description = "规格视图对象")
public class SpecVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "规格ID")
    private Long id;

    @Schema(description = "规格名称")
    private String name;

    @Schema(description = "输入类型(1文本 2单选 3多选)")
    private Integer inputType;

    @Schema(description = "可选项(逗号分隔)")
    private String options;

    @Schema(description = "排序")
    private Integer sort;

}
