package com.youlai.mall.product.model.query;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 分类查询对象
 *
 * @author Ray Hao
 * @since 2024/5/15
 */
@Schema(description = "分类查询对象")
@Data
public class CategoryQuery {

    @Schema(description = "关键字")
    private String keywords;
}
