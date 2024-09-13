package com.youlai.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 访问量统计VO
 *
 * @author Ray
 * @since 2024/7/2
 */
@Schema(description = "访问量统计VO")
@Getter
@Setter
public class VisitStatsVO {

    @Schema(description = "统计类型")
    private String type;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "今日访问量")
    private Integer todayCount;

    @Schema(description = "总访问量")
    private Integer totalCount;

    @Schema(description = "增长率")
    private BigDecimal growthRate;

    @Schema(description = "统计粒度标签")
    private String granularityLabel;

}
