package com.youlai.mall.product.model.query;

import com.youlai.common.base.BasePageQuery;
import com.youlai.mall.product.enums.AttributeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 属性分页查询对象
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@Schema(description ="属性分页查询对象")
@Data
public class AttributePageQuery extends BasePageQuery {

    @Schema(description="关键字(属性名称/属性组名称/分类名称)")
    private String keywords;

    @Schema(description="分类ID")
    private Long categoryId;

    @Schema(description="属性类型(1:基础属性,2:销售属性)")
    private Integer attributeType;

}
