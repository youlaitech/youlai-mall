package com.youlai.mall.product.model.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 商品分页查询对象
 *
 * @author Ray Hao
 * @since 2024/5/22
 */

@Schema(description = "商品分页查询对象")
@Data
public class ProductPageQuery extends BasePageQuery {

    @Schema(description="关键字")
    private String keywords;

    @Schema(description="商品分类ID")
    private Long categoryId;

    @Schema(description="上架状态（1：上架，0：下架）")
    private Integer status;

    @Schema(description="排序字段名")
    private String sortField;

    @Schema(description="排序规则(asc:升序;desc:降序)")
    private String sort;

}
