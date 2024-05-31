package com.youlai.mall.product.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youlai.mall.product.enums.AttributeInputMethodEnum;
import com.youlai.mall.product.enums.AttributeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 属性 分页VO
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@Getter
@Setter
@Schema(description = "属性")
public class AttributePageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "属性主键")
    private Long id;

    @Schema(description = "属性名称")
    private String name;

    @Schema(description = "属性类型标签")
    private String typeLabel;

    @Schema(description = "输入录入方式标签")
    private String inputMethodLabel;

    @Schema(description = "逗号分割的可选值列表，仅当input_method是2使用")
    private String selectableValues;

    @Schema(description = "属性组名称")
    private String groupName;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;

    @Schema(description = "排序")
    private Integer sort;

}