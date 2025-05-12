package com.youlai.mall.product.model.query.client;

import com.youlai.common.base.BasePageQuery;
import com.youlai.mall.product.enums.GoodsOrderByEnum;
import com.youlai.mall.product.enums.DirectionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户端商品分页查询对象
 *
 * @author Ray.Hao
 * @since 2024/5/17
 */

@EqualsAndHashCode(callSuper = false)
@Schema(description = "客户端商品分页查询对象")
@Data
public class ClientGoodsPageQuery extends BasePageQuery {

    @Schema(description = "关键字")
    private String keywords;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "排序字段：comprehensive-综合，price-价格，sales-销量")
    private String orderBy;

    @Schema(description = "排序方式：asc-升序 desc-降序")
    private String direction;

    @Schema(description = "最低价格（单位：分）")
    private Integer minPrice;

    @Schema(description = "最高价格（单位：分）")
    private Integer maxPrice;

}
