package com.youlai.mall.sms.pojo.form;

import com.youlai.mall.sms.pojo.vo.TemplateRuleVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xinyi
 * @desc: 优惠券模板创建请求对象
 * @date 2021/6/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponTemplateForm {

    private Long id;

    /**
     * 优惠券模板名称
     */
    @NotBlank(message = "请填写优惠券模板名称")
    private String name;

    /**
     * 优惠券 logo
     */
    private String logo;

    /**
     * 优惠券描述
     */
    @NotBlank(message = "请填写优惠券描述")
    private String desc;

    /**
     * 优惠券分类
     */
    @NotBlank(message = "请选择优惠券分类")
    private String category;

    /**
     * 优惠券产品线
     */
    @NotBlank(message = "请选择优惠券产品线")
    private Integer productLine;

    /**
     * 优惠券总数量
     */
    @NotNull(message = "请输入优惠券总数量")
    @Min(value = 1, message = "优惠券数量必须大于1")
    @Max(value = Integer.MAX_VALUE, message = "优惠券数量不能大于 " + Integer.MAX_VALUE)
    private Integer total;

    /**
     * 目标用户
     */
    private Integer target;

    /**
     * 优惠券规则
     */
    private TemplateRuleVO rule;


}
