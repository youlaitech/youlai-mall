package com.youlai.mall.pms.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Schema(description = "属性组主键")
    private Long attributeGroupId;

    @Schema(description = "属性名称")
    private String name;

    @Schema(description = "输入录入方式标签")
    private String inputTypeLabel;

    @Schema(description = "逗号分割的可选值列表，仅当input_type是2使用")
    private String options;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;
}