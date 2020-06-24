package com.fly4j.yshop.oms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel
public class OmsOrder extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(hidden = true)
    private Long id;
    @ApiModelProperty(hidden = true,value = "订单号")
    private String order_sn;

    @ApiModelProperty(value = "会员ID",example="1211480307950280706")
    private Long member_id;

    @ApiModelProperty(value="优惠券ID",example="1249738175305326593")
    private Long coupon_id;

    @ApiModelProperty(value = "订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单",example="0")
    private Integer status;

    @ApiModelProperty(value="订单类型：0->正常订单 1->秒杀订单",example="0")
    private Integer type;

    @ApiModelProperty(value="订单来源：0->PC订单；1->app订单",example="1")
    private Integer source_type;

    @ApiModelProperty(value="收货人姓名",example="张三")
    private String receiver_name;

    @ApiModelProperty(value="收货人电话",example="18856985645")
    private String receiver_mobile;

    @ApiModelProperty(value="收货人省",example="安徽省")
    private String receiver_province;

    @ApiModelProperty(value="收货人市",example="六安市")
    private String receiver_city;

    @ApiModelProperty(value="收货人区",example="金寨县")
    private String receiver_area;

    @ApiModelProperty(value="详细地址",example="安徽省金寨县梅山镇")
    private String receiver_detail_address;

    @ApiModelProperty(value="收货人邮编",example="237322")
    private String receiver_zip_code;

    @ApiModelProperty(value="买家留言",example="快点发货")
    private String buyer_message;

    @ApiModelProperty(value="运费金额",example="10")
    private BigDecimal freight_amount;

    @ApiModelProperty(value="优惠券抵扣金额",example="20")
    private BigDecimal coupon_amount;

    @ApiModelProperty(value="订单总额",example="200")
    private BigDecimal total_amount;

    @ApiModelProperty(value="应付总额",example="190")
    private BigDecimal pay_amount;

    @ApiModelProperty(value="物流公司",example="合肥仁宇货运")
    private String logistics_number;

    @ApiModelProperty(value="物流单号",example="849486456515113")
    private String logistics_company;

    @ApiModelProperty(value="支付方式：1->支付宝；2->微信；",example="1")
    private Integer pay_type;

    @ApiModelProperty(value="支付时间",example="2020-04-17 22:10:52")
    private Date pay_time;

    @ApiModelProperty(value="发货时间",example="2020-04-17 22:10:52")
    private Date deliver_time;

    @ApiModelProperty(value="确认收货时间",example="2020-04-18 22:10:52")
    private Date receive_time;

    @ApiModelProperty(value="评价时间",example="2020-04-19 22:10:52")
    private Date comment_time;

}
