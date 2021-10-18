package com.youlai.mall.sms.pojo.form;

import com.youlai.mall.sms.pojo.vo.TemplateRuleVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xinyi
 * @desc: 优惠券模板表单模型
 * @date 2021/6/26
 */
@ApiModel("优惠券模板表单模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponTemplateForm {

    @ApiModelProperty("优惠券模板ID(修改使用)")
    private String id;

    /**
     * 优惠券模板名称
     */
    @ApiModelProperty("优惠券模板名称(最大长度255)")
    @NotBlank(message = "请填写优惠券模板名称")
    @Length(max = 255,message = "长度在0到255个字符之间")
    private String name;

    /**
     * 优惠券 logo
     */
    @ApiModelProperty("优惠券 LOGO(最大长度255)")
    @Length(max = 255,message = "长度在0到255个字符之间")
    private String logo;

    /**
     * 优惠券描述
     */
    @ApiModelProperty("优惠券描述(最大长度255)")
    @NotBlank(message = "请填写优惠券描述")
    @Length(max = 255,message = "长度在0到255个字符之间")
    private String description;

    /**
     * 优惠券分类(满减券\折扣券\立减券)
     */
    @ApiModelProperty("优惠券分类(满减券、折扣券、立减券 ...)")
    @NotBlank(message = "请选择优惠券分类")
    private String categoryCode;

    /**
     * 优惠券总数量
     */
    @ApiModelProperty("优惠券总数量(范围1到65535)")
    @NotNull(message = "请输入优惠券总数量")
    @Min(value = 1, message = "优惠券数量必须大于1")
    @Max(value = 100000, message = "优惠券数量不能大于 " + 100000)
    private Integer total;

    @ApiModelProperty("优惠券发放时间范围")
    @NotNull(message = "优惠券使用时间范围不能为空")
    private Long[] offerTime;

    @ApiModelProperty("优惠券使用时间范围")
    @NotNull(message = "优惠券使用时间范围不能为空")
    private Long[] usedTime;

    /**
     * 优惠券规则
     */
    @ApiModelProperty("优惠券规则")
    @NotNull(message = "优惠券规则不能为空")
    private TemplateRuleVO rule;


}
