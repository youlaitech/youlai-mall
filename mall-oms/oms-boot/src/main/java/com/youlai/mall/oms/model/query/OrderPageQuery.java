package com.youlai.mall.oms.model.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * @author haoxr
 * @since 2022/2/1 19:14
 */
@Data
@Schema(description = "订单分页查询对象")
public class OrderPageQuery extends BasePageQuery {

    @Schema(description="订单状态")
    private Integer status;

    @Schema(description="会员ID")
    private Long memberId;

    @Schema(description="订单编号")
    private String orderSn;

    @Schema(description = "开始时间(格式：yyyy-MM-dd)")
    private String beginDate;

    @Schema(description = "截止时间(格式：yyyy-MM-dd)")
    private String endDate;

}
