package com.youlai.mall.product.model.vo;

import java.io.Serial;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 属性 VO
 *
 * @author Ray.Hao
 * @since 2024-04-19
 */
@Getter
@Setter
@Schema(description = "属性VO")
public class AttrVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "属性主键")
    private Long id;

    @Schema(description = "属性名称")
    private String name;

    @Schema(description = "属性输入类型：1->手动输入；2->列表选择")
    private Integer inputType;

    @Schema(description = "可选列表(仅当input_type是2使用)",example = "1920*1080,2560*1440,3840*2160")
    private String options;

    @Schema(description = "排序")
    private Integer sort;
}
