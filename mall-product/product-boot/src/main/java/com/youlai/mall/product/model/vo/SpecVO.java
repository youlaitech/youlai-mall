package com.youlai.mall.product.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 规格 VO
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

    @Schema(description = "输入方式（1：手动输入，2：列表选择）")
    private Byte inputType;

    @Schema(description = "可选值列表（以逗号分隔，仅当输入方式为2时使用）")
    private String options;

}
