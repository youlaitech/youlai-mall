package com.youlai.mall.pms.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 属性组 分页VO
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@Getter
@Setter
@Schema(description = "属性组")
public class AttributeGroupPageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "属性组ID")
    private Long id;

    @Schema(description = "属性组名称")
    private String name;

    @Schema(description = "排序")
    private Short sort;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;

}
