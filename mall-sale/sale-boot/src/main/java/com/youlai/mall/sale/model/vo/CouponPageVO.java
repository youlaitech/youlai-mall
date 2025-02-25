package com.youlai.mall.sale.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 优惠券分页视图对象
 *
 * @author Ray.Hao
 * @since 2022/6/26
 */
@Schema(description = "优惠券分页视图对象")
@Data
public class CouponPageVO {

    @Schema(description="ID")
    private Long id;

    @Schema(description="优惠券名称")
    private String name;

    @Schema(description="优惠券码")
    private String code;

    @Schema(description="使用平台")
    private String platformLabel;

    @Schema(description="优惠券类型标签")
    private String typeLabel;

    @Schema(description="优惠券面值")
    private String faceValueLabel;

    @Schema(description="使用门槛")
    private String minPointLabel;

    @Schema(description="优惠券有效期")
    private String validityPeriodLabel;

    @Schema(description="使用说明")
    private String remark;

}
