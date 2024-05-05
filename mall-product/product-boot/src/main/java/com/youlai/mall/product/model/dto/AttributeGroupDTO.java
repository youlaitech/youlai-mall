package com.youlai.mall.product.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * 属性组 DTO
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@Getter
@Setter
@Schema(name = "AttributeGroup传输层对象", description = "属性组")
public class AttributeGroupDTO implements Serializable {

    private static final long serialVersionUID = 1L;

        @Schema(description = "属性组主键")

private Long id;

        @Schema(description = "属性组名称")

private String name;

        @Schema(description = "排序")

private Short sort;

        @Schema(description = "备注")

private String remark;

        @Schema(description = "创建时间")

private LocalDateTime createTime;

        @Schema(description = "更新时间")

private LocalDateTime updateTime;

        @Schema(description = "逻辑删除标识(0-未删除，1-已删除)")

private Integer isDeleted;;
}
