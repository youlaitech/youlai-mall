package com.youlai.mall.product.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 属性组VO
 *
 * @author Ray
 * @since 2024/6/11
 */
@Schema( description = "属性组VO")
@Getter
@Setter
public class AttributeGroupVO {

    @Schema(description = "属性组ID",example = "1")
    private Long groupId;

    @Schema(description = "属性组名称",example = "屏幕属性")
    private String groupName;

    @Schema(description = "属性列表")
    private List<AttributeVO> attributes;

}
