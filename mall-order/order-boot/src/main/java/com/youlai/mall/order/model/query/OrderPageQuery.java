package com.youlai.mall.order.model.query;

import com.youlai.common.base.BasePageQuery;
 import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 订单分页查询对象
 *
 * @author haoxr
 * @since 2.3.0
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description ="订单分页查询对象")
@Data
public class OrderPageQuery extends BasePageQuery {

    /**
     * 关键字(订单编号/商品名称/会员姓名/会员手机号)
     */
    @Schema(description="关键字(订单编号/商品名称/会员姓名/会员手机号)")
    private String keywords;

    /**
     * 订单状态
     */
    @Schema(description="订单状态")
    private Integer status;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间(yyyy-MM-dd)",example = "2023-10-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd 00:00:00") // DateTimeFormat 用于将查询参数或表单参数转换为日期类型
    private Date beginDate;

    /**
     * 截止时间
     */
    @Schema(description = "截止时间(yyyy-MM-dd)",example = "2025-10-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd 23:59:59")
    private Date endDate;

}
