package com.youlai.mall.order.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
 import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * App-订单分页视图对象
 *
 * @author Ray.Hao
 * @since 2.3.0
 */
@Schema(description ="App-订单分页视图对象")
@Data
public class OrderPageAppVO {

    @Schema(description="订单ID")
    private Long id;

    @Schema(description="订单编号")
    private String orderSn;

    @Schema(description="订单总金额(分)")
    private BigDecimal totalFee;

    @Schema(description="订单总金额(分)")
    private Long paymentAmount;

    @Schema(description="支付方式标签")
    private String paymentMethodLabel;

    @Schema(description="订单状态")
    private Integer status;

    @Schema(description="订单状态标签")
    private String statusLabel;

    @Schema(description="商品总数")
    private Integer totalQuantity;

    @Schema(description="订单创建时间")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date createTime;

    @Schema(description="订单来源标签")
    private String sourceLabel;

    @Schema(description="订单备注")
    private String remark;

    @Schema(description="订单商品集合")
    private List<OrderPageAdminVO.OrderItem> orderItems;

    @Schema(description ="订单商品明细")
    @Data
    public static class OrderItem {

        @Schema(description="商品ID")
        private Long skuId;

        @Schema(description="商品规格名称")
        private String skuName;

        @Schema(description="图片地址")
        private String imgUrl;

        @Schema(description="商品价格")
        private Long price;

        @Schema(description="商品数量")
        private Integer quantity;

        @Schema(description="商品总金额(单位：分)")
        private Long totalFee;

    }

}
