package com.youlai.mall.product.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 属性 DTO
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@Getter
@Setter
@Schema(name = "Attribute传输层对象", description = "属性")
public class AttributeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "属性主键")
    private Long id;

    @Schema(description = "属性组主键")
    private Long attributeGroupId;

    @Schema(description = "属性名称")
    private String name;

    @Schema(description = "输入录入方式：1-手动输入，2-从列表选择")
    private Integer inputType;

    @Schema(description = "逗号分割的可选值列表，仅当input_type是2使用")
    private String options;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除标识(0-未删除，1-已删除)")
    private Integer isDeleted;
}
