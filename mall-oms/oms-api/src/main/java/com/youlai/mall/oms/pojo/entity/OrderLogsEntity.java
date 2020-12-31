package com.youlai.mall.oms.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单操作历史记录
 * 
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@Data
@TableName("oms_order_logs")
public class OrderLogsEntity extends BaseEntity {

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 操作人[用户；系统；后台管理员]
	 */
	private String user;
	/**
	 * 操作详情
	 */
	private String detail;
	/**
	 * 操作时订单状态
	 */
	private Integer orderStatus;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 逻辑删除【0->正常；1->已删除】
	 */
	private Integer deleted;

}
