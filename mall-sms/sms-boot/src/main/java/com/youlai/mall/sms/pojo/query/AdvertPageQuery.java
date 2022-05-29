package com.youlai.mall.sms.pojo.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xinyi
 * @desc: 优惠券领取使用详情条件分页查询
 * @date 2021/7/11
 */
@ApiModel("优惠券领取使用详情条件分页查询")
@Data
public class AdvertPageQuery extends BasePageQuery {

    @ApiModelProperty("广告)")
    private String title;

}
