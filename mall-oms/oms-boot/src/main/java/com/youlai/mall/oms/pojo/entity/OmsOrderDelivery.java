package com.youlai.mall.oms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 订单物流记录表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@Data
@Builder
public class OmsOrderDelivery extends BaseEntity {

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
	 * 物流公司(配送方式)
	 */
	private String deliveryCompany;
	/**
	 * 物流单号
	 */
	private String deliverySn;
	/**
	 * 收货人姓名
	 */
	private String receiverName;
	/**
	 * 收货人电话
	 */
	private String receiverPhone;
	/**
	 * 收货人邮编
	 */
	private String receiverPostCode;
	/**
	 * 省份/直辖市
	 */
	private String receiverProvince;
	/**
	 * 城市
	 */
	private String receiverCity;
	/**
	 * 区
	 */
	private String receiverRegion;
	/**
	 * 详细地址
	 */
	private String receiverDetailAddress;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 物流状态【0->运输中；1->已收货】
	 */
	private Integer deliveryStatus;
	/**
	 * 发货时间
	 */
	private Date deliveryTime;
	/**
	 * 确认收货时间
	 */
	private Date receiveTime;
	/**
	 * 逻辑删除【0->正常；1->已删除】
	 */
	private Integer deleted;


}
