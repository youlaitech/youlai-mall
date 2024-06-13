package com.youlai.mall.product.model.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页查询对象
 *
 * @author Ray Hao
 * @since 2024-06-13
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description ="分页查询对象")
@Data
public class SpecPageQuery extends BasePageQuery {

    @Schema(description="关键字")
    private String keywords;

}
