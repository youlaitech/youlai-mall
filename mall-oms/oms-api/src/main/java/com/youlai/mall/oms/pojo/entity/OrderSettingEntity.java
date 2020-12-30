package com.youlai.mall.oms.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单配置信息
 * 
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@Data
@TableName("oms_order_setting")
public class OrderSettingEntity extends BaseEntity {

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 秒杀订单超时关闭时间(分)
	 */
	private Integer flashOrderOvertime;
	/**
	 * 正常订单超时时间(分)
	 */
	private Integer normalOrderOvertime;
	/**
	 * 发货后自动确认收货时间（天）
	 */
	private Integer confirmOvertime;
	/**
	 * 自动完成交易时间，不能申请退货（天）
	 */
	private Integer finishOvertime;
	/**
	 * 订单完成后自动好评时间（天）
	 */
	private Integer commentOvertime;
	/**
	 * 会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】
	 */
	private Integer memberLevel;
	/**
	 * 逻辑删除【0->正常；1->已删除】
	 */
	private Integer deleted;

}
