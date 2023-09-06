package com.youlai.mall.oms.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Admin-订单分页视图对象
 *
 * @author haoxr
 * @since 2.3.0
 */
@ApiModel("管理端-订单分页视图对象")
@Data
public class OmsOrderPageVO {

    @ApiModelProperty("订单ID")
    private Long id;

    @ApiModelProperty("订单编号")
    private String orderSn;

    @ApiModelProperty("订单总金额(分)")
    private BigDecimal totalAmount;

    @ApiModelProperty("订单总金额(分)")
    private Long paymentAmount;

    @ApiModelProperty("支付方式标签")
    private String paymentMethodLabel;

    @ApiModelProperty("订单状态")
    private Integer status;

    @ApiModelProperty("订单状态标签")
    private String statusLabel;

    @ApiModelProperty("商品总数")
    private Integer totalQuantity;

    @ApiModelProperty("订单创建时间")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("订单来源标签")
    private String sourceLabel;

    @ApiModelProperty("订单备注")
    private String remark;

    @ApiModelProperty("订单商品集合")
    private List<OrderItem> orderItems;

    @ApiModel("订单商品明细")
    @Data
    public static class OrderItem {

        @ApiModelProperty("商品ID")
        private Long skuId;

        @ApiModelProperty("商品规格名称")
        private String skuName;

        @ApiModelProperty("图片地址")
        private String picUrl;

        @ApiModelProperty("商品价格")
        private Long price;

        @ApiModelProperty("商品数量")
        private Integer quantity;

        @ApiModelProperty("商品总金额(单位：分)")
        private Long totalAmount;

    }

}
