package com.youlai.mall.product.model.query;

import com.youlai.common.base.BasePageQuery;
import com.youlai.mall.product.enums.ProductOrderByEnum;
import com.youlai.mall.product.enums.SortEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品分页查询对象
 *
 * @author Ray Hao
 * @since 2024/5/22
 */

@EqualsAndHashCode(callSuper = true)
@Schema(description = "商品分页查询对象")
@Data
public class ProductPageQuery extends BasePageQuery {

    @Schema(description="关键字")
    private String keywords;

    @Schema(description="商品分类ID")
    private Long categoryId;

    @Schema(description="排序字段名（score:评分，price：价格，sales：销量）",example = "sales")
    private ProductOrderByEnum orderBy;

    @Schema(description="排序规则（asc:升序;desc:降序）",example = "desc")
    private SortEnum sort;

}
