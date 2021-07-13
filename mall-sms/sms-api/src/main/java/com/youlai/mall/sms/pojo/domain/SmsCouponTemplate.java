package com.youlai.mall.sms.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.mall.sms.pojo.enums.CouponCategoryEnum;
import com.youlai.mall.sms.pojo.enums.CouponTemplateStateEnum;
import com.youlai.mall.sms.pojo.enums.DistributeTargetEnum;
import com.youlai.mall.sms.pojo.vo.TemplateRuleVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author xinyi
 * @desc: 优惠券模板实体类：基础属性 + 规则属性
 * @date 2021/6/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsCouponTemplate {

    /**
     * 主键自增ID
     */
    @TableId
    private Long id;

    /**
     * 优惠券模板状态(0:待审核、1:进行中、2:已过期)
     */
    private CouponTemplateStateEnum state;

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
    private String description;

    /**
     * 优惠券分类
     */
    private CouponCategoryEnum category;

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

    private Date gmtCreate;

    private String gmtCreatedBy;

    private Date gmtModified;

    private String gmtModifiedBy;
}
