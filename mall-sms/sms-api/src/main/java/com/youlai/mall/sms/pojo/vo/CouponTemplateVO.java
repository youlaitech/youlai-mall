package com.youlai.mall.sms.pojo.vo;

import com.youlai.common.base.BaseVO;
import com.youlai.mall.sms.pojo.enums.CouponCategoryEnum;
import com.youlai.mall.sms.pojo.enums.DistributeTargetEnum;
import com.youlai.mall.sms.pojo.enums.ProductLineEnum;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xinyi
 * @desc: 优惠券模板实体类
 * @date 2021/7/3
 */
@ApiModel(value = "优惠券模板模型",description = "优惠券模板模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponTemplateVO extends BaseVO {

    /**
     * 主键自增ID
     */
    private Long id;

    /**
     * 是否是可用状态
     */
    private Integer available;

    /**
     * 是否过期
     */
    private Integer expired;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠券logo
     */
    private String logo;

    /**
     * 优惠券描述
     */
    private String intro;

    /**
     * 优惠券分类
     */
    private CouponCategoryEnum category;

    /**
     * 产品线
     */
    private ProductLineEnum productLine;

    /**
     * 总数
     */
    private Integer total;

    /**
     * 优惠券模板编码
     */
    private  String code;

    /**
     * 目标用户
     */
    private DistributeTargetEnum target;

    /**
     * 优惠券规则
     */
    private TemplateRuleVO rule;

}
