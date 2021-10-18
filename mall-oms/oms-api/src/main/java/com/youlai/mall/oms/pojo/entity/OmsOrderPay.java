package com.youlai.mall.oms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 支付信息表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@Data
@Builder
public class OmsOrderPay extends BaseEntity {

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 订单ID
	 */
	private Long orderId;
	/**
	 * 支付流水号
	 */
	private String paySn;
	/**
	 * 应付总额(分)
	 */
	private Long payAmount;
	/**
	 * 支付时间
	 */
	private Date payTime;
	/**
	 * 支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】
	 */
	private Integer payType;
	/**
	 * 支付状态
	 */
	private Integer payStatus;
	/**
	 * 确认时间
	 */
	private Date confirmTime;
	/**
	 * 回调内容
	 */
	private String callbackContent;
	/**
	 * 回调时间
	 */
	private Date callbackTime;
	/**
	 * 交易内容
	 */
	private String paySubject;
	/**
	 * 逻辑删除【0->正常；1->已删除】
	 */
	private Integer deleted;


}
