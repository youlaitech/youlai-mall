package com.youlai.mall.order.model.form;

import com.youlai.mall.order.enums.OrderSourceEnum;
 import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import  jakarta.validation.constraints.NotBlank;
import  jakarta.validation.constraints.NotEmpty;
import  jakarta.validation.constraints.NotNull;
import  jakarta.validation.constraints.Size;
import java.util.List;

/**
 * 订单提交表单对象
 *
 * @author Ray.Hao
 * @since 2.0.0
 */
@Data
@ToString
public class OrderSubmitForm {

    @Schema(description="订单确认页面签发的令牌(防止重复提交)")
    @NotBlank(message = "订单令牌不能为空")
    private String orderToken;

    @Schema(description="订单来源")
    @NotNull(message = "订单来源不能为空")
    private OrderSourceEnum orderSource;

    @Schema(description="订单商品明细")
    @NotEmpty(message = "订单商品不能为空")
    private List<OrderItem> orderItems;

    @Schema(description="应付金额(单位：分)")
    @NotNull(message = "应付金额不能为空")
    private Long paymentAmount;

    @Schema(description="收获地址")
    @NotNull(message = "收货地址不能为空")
    private ShippingAddress shippingAddress;

    @Schema(description="订单备注")
    @Size(max = 500, message = "订单备注长度不能超过500")
    private String remark;

    @Schema(description ="收获地址")
    @Data
    public static class ShippingAddress {

        @Schema(description="收货人姓名")
        private String consigneeName;

        @Schema(description="收货人手机号")
        private String consigneeMobile;

        @Schema(description="省份")
        private String province;

        @Schema(description="城市")
        private String city;

        @Schema(description="区域")
        private String district;

        @Schema(description="详细地址")
        private String detailAddress;
    }

    @Schema(description ="订单商品")
    @Data
    public static class OrderItem {

        @Schema(description = "SKU ID")
        private Long skuId;

        @Schema(description = "SKU 编号")
        private String skuCode;

        @Schema(description = "SKU 名称")
        private String skuName;

        @Schema(description = "商品图片URL")
        private String imgUrl;

        @Schema(description = "商品价格(单位:分)")
        private Long skuPrice;

        @Schema(description = "商品名称")
        private String spuName;

        @Schema(description = "商品数量")
        private Integer skuQuantity;
    }


}
