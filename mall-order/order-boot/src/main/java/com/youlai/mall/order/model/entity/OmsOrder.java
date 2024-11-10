package com.youlai.mall.order.model.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 订单详情表
 *
 * @author huawei
 * @since 2.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OmsOrder extends BaseEntity {


	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 订单总额（分）
	 */
	private Long totalFee;

	/**
	 * 订单状态
	 */
	private Integer status;
	/**
	 * 订单备注
	 */
	private String remark;
	/**
	 * 会员id
	 */
	private Long memberId;
	/**
	 * 使用的优惠券
	 */
	private Long couponId;
	/**
	 * 优惠券抵扣金额（分）
	 */
	private Long couponAmount;
	/**
	 * 运费金额（分）
	 */
	private Long freightAmount;
	/**
	 * 应付总额（分）
	 */
	private Long paymentAmount;
	/**
	 * 支付时间
	 */
	private Date paymentTime;
	/**
	 * 支付方式【1->微信jsapi；2->支付宝；3->余额；4->微信app；】
	 */
	private Integer paymentMethod;
	/**
	 * 商户订单号
	 */
	@TableField(updateStrategy = FieldStrategy.ALWAYS)
	private String outTradeNo;
	/**
	 * 微信支付订单号
	 */
	private String transactionId;
	/**
	 * 商户退款单号
	 */
	private String outRefundNo;
	/**
	 * 微信支付退款单号
	 */
	private String refundId;
	/**
	 * 发货时间
	 */
	private Date deliveryTime;
	/**
	 * 确认收货时间
	 */
	private Date receiveTime;
	/**
	 * 评价时间
	 */
	private Date commentTime;

	@TableField(exist = false)
	private List<OmsOrderItem> orderItems;

	/**
	 * 逻辑删除标识(0-未删除 1-已删除)
	 */
	private Integer isDeleted;


}
