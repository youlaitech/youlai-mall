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

    @Schema(description = "属性组名称")
    private String groupName;

    @Schema(description = "属性名称")
    private String name;

    @Schema(description = "输入类型标签")
    private Integer inputTypeLabel;

    @Schema(description = "可选项(逗号分隔)",example = "1920*1080,2560*1440,3840*2160")
    private String options;

    @Schema(description = "排序")
    private Integer sort;
}
