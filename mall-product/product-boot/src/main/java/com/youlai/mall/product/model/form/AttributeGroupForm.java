package com.youlai.mall.product.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 属性组表单对象
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@Getter
@Setter
@Schema(description = "属性组表单对象")
public class AttributeGroupForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "属性组主键")
    private Long id;

    @Schema(description = "属性组名称")
    private String name;

    @Schema(description = "排序")
    private Short sort;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;


}
