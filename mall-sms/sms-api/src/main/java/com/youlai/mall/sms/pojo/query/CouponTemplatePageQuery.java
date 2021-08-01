package com.youlai.mall.sms.pojo.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author xinyi
 * @desc: 优惠券模板条件分页查询
 * @date 2021/7/11
 */
@ApiModel("优惠券模板条件分页查询")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponTemplatePageQuery extends BasePageQuery {

    /**
     * 优惠券模板名称(模糊匹配)
     */
    @ApiModelProperty("优惠券模板名称(模糊匹配)")
    private String name;

    @ApiModelProperty("优惠券模板状态")
    private Integer state;

    @ApiModelProperty("优惠券模板目标发放用户")
    private Integer target;

    @ApiModelProperty("优惠券模板类型")
    private Integer category;

    @ApiModelProperty("优惠券模板创建范围开始时间")
    private Date startTime;

    @ApiModelProperty("优惠券模板创建范围结束时间")
    private Date endTime;
}
