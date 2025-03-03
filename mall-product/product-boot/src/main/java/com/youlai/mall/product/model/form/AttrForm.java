package com.youlai.mall.product.model.form;

import com.youlai.mall.product.enums.AttributeInputTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 属性表单对象
 *
 * @author Ray.Hao
 * @since 2024-04-19
 */
@Getter
@Setter
@Schema(description = "属性表单对象")
public class AttrForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "属性ID",example = "1")
    private Long id;

    @Schema(description = "属性组名称",example = "1")
    private String groupName;

    @Schema(description = "分类ID",example = "1")
    private Long categoryId;

    @Schema(description = "属性名称",example = "屏幕分辨率")
    private String name;

    @Schema(description = "输入方式（1：手动输入，2：从列表选择）",example = "2")
    private Integer inputType;

    @Schema(description = "可选值列表（以逗号分隔，仅当输入方式为2时使用）",example = "1920x1080,2560x1440")
    private String options;

    @Schema(description = "排序",example = "1")
    private Integer sort;
}
