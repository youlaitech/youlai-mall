package com.youlai.mall.product.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 属性查询对象
 *
 * @author Ray.Hao
 * @since 2024-04-19
 */
@Schema(description ="属性查询对象")
@EqualsAndHashCode(callSuper = false)
@Data
public class AttrQuery {

    @Schema(description="关键字(属性名称/分类名称)")
    private String keywords;

    @Schema(description="分类ID")
    private Long categoryId;
    
}
