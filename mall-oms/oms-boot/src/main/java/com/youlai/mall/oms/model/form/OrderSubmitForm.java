package com.youlai.mall.oms.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 订单提交表单对象
 *
 * @author haoxr
 * @since 2.0.0
 */
@Data
@ToString
public class OrderSubmitForm {

    @ApiModelProperty("订单确认页面签发的令牌(防止重复提交)")
    @NotBlank(message = "订单令牌不能为空")
    private String orderToken;

    @ApiModelProperty("订单来源")
    @NotNull(message = "订单来源不能为空")
    private Integer sourceType;

    @ApiModelProperty("订单商品明细")
    @NotEmpty(message = "订单商品不能为空")
    private List<OrderItem> orderItems;

    @ApiModelProperty("应付金额(单位：分)")
    @NotNull(message = "应付金额不能为空")
    private Long paymentAmount;

    @ApiModelProperty("收获地址")
    @NotNull(message = "收货地址不能为空")
    private ShippingAddress shippingAddress;

    @ApiModelProperty("订单备注")
    @Size(max = 500, message = "订单备注长度不能超过500")
    private String remark;

    @ApiModel("收获地址")
    @Data
    public static class ShippingAddress {

        @ApiModelProperty("收货人姓名")
        private String consigneeName;

        @ApiModelProperty("收货人手机号")
        private String consigneeMobile;

        @ApiModelProperty("省份")
        private String province;

        @ApiModelProperty("城市")
        private String city;

        @ApiModelProperty("区域")
        private String district;

        @ApiModelProperty("详细地址")
        private String detailAddress;
    }

    @ApiModel("订单商品")
    @Data
    public static class OrderItem {

        @ApiModelProperty(value = "SKU ID")
        private Long skuId;

        @ApiModelProperty(value = "SKU 编号")
        private String skuSn;

        @ApiModelProperty(value = "SKU 名称")
        private String skuName;

        @ApiModelProperty(value = "商品图片URL")
        private String picUrl;

        @ApiModelProperty(value = "商品价格(单位:分)")
        private Long price;

        @ApiModelProperty(value = "商品名称")
        private String spuName;

        @ApiModelProperty(value = "商品数量")
        private Integer count;
    }


}
