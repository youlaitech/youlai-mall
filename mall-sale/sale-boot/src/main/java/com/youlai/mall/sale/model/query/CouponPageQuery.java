package com.youlai.mall.sale.model.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xinyi
 * @desc: 优惠券分页查询对象
 * @since 2021/7/11
 */
@Schema(description = "优惠券分页查询对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponPageQuery extends BasePageQuery {

    @Schema(description="状态")
    private Integer status;

    @Schema(description="优惠券码")
    private String code;
}
