package com.youlai.mall.product.model.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 规格分页VO
 *
 * @author Ray.Hao
 * @since 2024-06-13
 */
@Getter
@Setter
@Schema(description = "规格分页视图对象")
public class SpecPageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "属性")
    private Long id;

    @Schema(description = "属性名称")
    private String name;

    @Schema(description = "输入方式Label")
    private String inputTypeLabel;

    @Schema(description = "可选值（以逗号分隔，仅当输入方式为2时使用）")
    private String options;

    @Schema(description = "分类ID")
    private String categoryId;

    @Schema(description = "排序")
    private Short sort;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
